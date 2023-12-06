package com.group16.hams;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.group16.hams.doctor.AddShift;
import com.group16.hams.doctor.RecyclerViewHolderAppointmentDoctor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import entities.*;

public class AppointmentClickedDoctor extends AppCompatActivity {

    RecyclerViewHolderAppointmentDoctor curHolder;
    int index;

    TextView appointmentDate, appointmentTime, curApprovalStatus, patientName, patientUsername,
            patientPhoneNumber, patientAddress, patientHealthCard;
    Patient curPatient;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("REACHED");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_clicked_doctor);

        Intent intent = getIntent();
        curHolder = intent.getParcelableExtra("Appointment Holder");
        index = intent.getIntExtra("index",-1);

        this.email = curHolder.getAppointment().getAppointmentPatientEmail();
        System.out.println(email + "This is da email");

        Database.getPatient(email, new Database.MyCallBack() {
            @Override
            public void onCallBack(Patient p) {
                if (p == null) {
                    System.out.println("Looping 1");
                    //Might have to add more precautions here in case the patient gets deleted
                    //after the appointment has already been created. Otherwise this should never occur
                    System.out.println("Patient is not in database.");
                }

                else {
                    //MOVED HERE.... WAITS FOR DATABASE TO RETRIEVE INFO
                    System.out.println("Looping 2");
                    System.out.println("P: " + p);
                    curPatient = p;
                    patientUsername.setText("Patient Username: " + curPatient.getUsername());
                    patientPhoneNumber.setText("Patient Phone Number: " + curPatient.getPhoneNumber());
                    patientAddress.setText("Patient Address: " + curPatient.getAddress());
                    patientHealthCard.setText("Patient Health Card Number: " + curPatient.getHealthCardNumber());

                }
            }
        });

        appointmentDate = findViewById(R.id.appointmentDate);
        appointmentTime = findViewById(R.id.appointmentTime);
        curApprovalStatus = findViewById(R.id.currentApprovalStatus);
        patientName = findViewById(R.id.patientName);
        patientUsername = findViewById(R.id.patientUsername);
        patientPhoneNumber = findViewById(R.id.patientPhoneNumber);
        patientAddress = findViewById(R.id.patientAddress);
        patientHealthCard = findViewById(R.id.patientHealthCardNumber);

        appointmentDate.setText("Appointment Date: " + curHolder.getAppointmentDate());
        appointmentTime.setText("Appointment Time: " + curHolder.getAppointmentTime());
        curApprovalStatus.setText("Current Approval Status: " + curHolder.getAppointmentApproval());
        patientName.setText("Patient Name: " + curHolder.getAppointmentPatientName());

        Button approveButton = (Button)findViewById(R.id.approveAppointmentButton);
        Button cancelButton = (Button)findViewById(R.id.cancelAppointmentButton);

        if (!curHolder.getAppointment().isUpcoming()) {
            approveButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
        }

        else {
            approveButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickCancelAppointmentButton(View view) {
        //Change the status of the appointment to CANCELLED
        if (curHolder.getAppointment().getStatus() == Appointment.REJECTED_APPOINTMENT) {
            Toast t = new Toast(AppointmentClickedDoctor.this);
            t.makeText(AppointmentClickedDoctor.this, "Appointment had already been cancelled",
                    Toast.LENGTH_SHORT).show();
        }

        else {
            curHolder.getAppointment().setStatus(Appointment.REJECTED_APPOINTMENT);

            curApprovalStatus = findViewById(R.id.currentApprovalStatus);
            curApprovalStatus.setText("Current Approval Status: " + curHolder.getAppointmentApproval());
            Database.changeAppointmentStatus(curHolder.getAppointment(), Appointment.REJECTED_APPOINTMENT);

            Appointment delPatientAppointment = new Appointment(curHolder.getAppointment().getAppointmentPatientEmail(),
                    curHolder.getAppointment().getStartDateAndTimeString());


            ((Doctor) Database.currentUser).removeAppointment(delPatientAppointment);


            (new Handler()).post(new Runnable() {
                @Override
                public void run() {
                    Database.deleteDoctorAppointmentThroughDoctor(delPatientAppointment);
                }
            });

            String formatChange[] = curHolder.getAppointment().getStartDateAndTimeString().split(" ");
            String startTimeString = formatChange[1];
            LocalTime startTime = LocalTime.parse(startTimeString, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = startTime.plusMinutes(30);
            String endTimeString = endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            String startDateAndTimeString = formatChange[0] + " " + startTimeString + " " + endTimeString;
            TimeSlot delDoctorAppointment = new TimeSlot(((Doctor) Database.currentUser).
                    getUsername(), startDateAndTimeString, ((Doctor) Database.currentUser).getSpecialties());

            //getting the patient associated with the appointment
            Database.getPatientWithID(curHolder.getAppointment().getAppointmentPatientEmail(), new Database.PatientWithIDCallBack() {
                @Override
                public void PatientWithIDCallBack(Patient p, String patientID) {
                    p.removePatientAppointment(delDoctorAppointment);
                    //updating patient database
                    (new Handler()).post(new Runnable() {
                        @Override
                        public void run() {
                            Database.deletePatientAppointmentThroughDoctor(delDoctorAppointment, patientID);
                        }
                    });
                }
            });

            /*
            Database.getAllPatients(new Database.AllPatientsCallBack() {
                @Override
                public void onAllPatientsCallBack(ArrayList<Patient> patients, ArrayList<String> patientIDs) {
                    for (String patientID : patientIDs) {
                        Database.changeTimeSlotStatus(delDoctorAppointment,
                                TimeSlot.UNBOOKED_APPOINTMENT, patientID);
                    }
                }
            });

             */

            Database.changeAllTimeslotStatuses(delDoctorAppointment, TimeSlot.UNBOOKED_APPOINTMENT);

            Toast.makeText(AppointmentClickedDoctor.this, "Canceled!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onClickApproveAppointmentButton(View view) {
        //Change the status of the appointment to APPROVED
        if (curHolder.getAppointment().getStatus() == Appointment.APPROVED_APPOINTMENT) {
            Toast t = new Toast(AppointmentClickedDoctor.this);
            t.makeText(AppointmentClickedDoctor.this, "Appointment had already been approved",
                    Toast.LENGTH_SHORT).show();
        }

        else {
            curHolder.getAppointment().setStatus(Appointment.APPROVED_APPOINTMENT);

            curApprovalStatus = findViewById(R.id.currentApprovalStatus);
            curApprovalStatus.setText("Current Approval Status: " + curHolder.getAppointmentApproval());
            Database.changeAppointmentStatus(curHolder.getAppointment(), Appointment.APPROVED_APPOINTMENT);
        }
    }

    public void onClickReturnToAppointmentsButton(View view) {
        finish();
    }
}