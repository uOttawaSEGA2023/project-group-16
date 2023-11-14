package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.group16.hams.doctor.AppointmentsDoctor;
import com.group16.hams.doctor.RecyclerViewHolderAppointment;

import entities.*;

public class AppointmentClicked extends AppCompatActivity {

    RecyclerViewHolderAppointment curHolder;
    int index;

    TextView appointmentDate, appointmentTime, curApprovalStatus, patientName, patientUsername,
            patientPhoneNumber, patientAddress, patientHealthCard;
    Patient curPatient;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("REACHED");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_clicked);

        Intent intent = getIntent();
        curHolder = intent.getParcelableExtra("Appointment Holder");
        index = intent.getIntExtra("index",-1);

        this.email = curHolder.getAppointment().getAppointmentPatientEmail();
        System.out.println(email + "This is da email");

        Database.getPatient(email, new Database.MyCallBack() {
            @Override
            public void onCallBack(Patient p) {
                if (p == null) {
                    //Might have to add more precautions here in case the patient gets deleted
                    //after the appointment has already been created. Otherwise this should never occur
                    System.out.println("Patient is not in database.");
                }

                else {
                    //MOVED HERE.... WAITS FOR DATABASE TO RETRIEVE INFO
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

    public void onClickCancelAppointmentButton(View view) {
        //Change the status of the appointment to CANCELLED
        if (curHolder.getAppointment().getStatus() == Appointment.REJECTED_APPOINTMENT) {
            Toast t = new Toast(AppointmentClicked.this);
            t.makeText(AppointmentClicked.this, "Appointment had already been cancelled",
                    Toast.LENGTH_SHORT).show();
        }

        else {
            curHolder.getAppointment().setStatus(Appointment.REJECTED_APPOINTMENT);

            curApprovalStatus = findViewById(R.id.currentApprovalStatus);
            curApprovalStatus.setText("Current Approval Status: " + curHolder.getAppointmentApproval());
            Database.changeAppointmentStatus(curHolder.getAppointment(), Appointment.REJECTED_APPOINTMENT);

            if (index != -1) {
                if (curHolder.getAppointment().isUpcoming())
                    AppointmentsDoctor.upcomingAdapter.notifyItemChanged(this.index);
                else
                    AppointmentsDoctor.pastAdapter.notifyItemChanged(this.index);
            }

        }
    }

    public void onClickApproveAppointmentButton(View view) {
        //Change the status of the appointment to APPROVED
        if (curHolder.getAppointment().getStatus() == Appointment.APPROVED_APPOINTMENT) {
            Toast t = new Toast(AppointmentClicked.this);
            t.makeText(AppointmentClicked.this, "Appointment had already been approved",
                    Toast.LENGTH_SHORT).show();
        }

        else {
            curHolder.getAppointment().setStatus(Appointment.APPROVED_APPOINTMENT);

            curApprovalStatus = findViewById(R.id.currentApprovalStatus);
            curApprovalStatus.setText("Current Approval Status: " + curHolder.getAppointmentApproval());
            Database.changeAppointmentStatus(curHolder.getAppointment(), Appointment.APPROVED_APPOINTMENT);
            if (index != -1) {
                if (curHolder.getAppointment().isUpcoming())
                    AppointmentsDoctor.upcomingAdapter.notifyItemChanged(this.index);
                else
                    AppointmentsDoctor.pastAdapter.notifyItemChanged(this.index);
            }
        }
    }

    public void onClickReturnToAppointmentsButton(View view) {
        finish();
    }
}