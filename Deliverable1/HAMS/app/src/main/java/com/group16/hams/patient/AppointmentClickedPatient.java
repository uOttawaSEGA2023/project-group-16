package com.group16.hams.patient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.group16.hams.AppointmentClickedDoctor;
import com.group16.hams.Database;
import com.group16.hams.R;
import com.group16.hams.doctor.AddShift;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import entities.Appointment;
import entities.Doctor;
import entities.Patient;
import entities.Shift;
import entities.TimeSlot;

public class AppointmentClickedPatient extends AppCompatActivity {

    TimeSlotHolder curHolder;

    TextView appointmentDate, appointmentStartTime, appointmentEndTime, doctorName, doctorUsername,
            doctorPhoneNumber;

    Doctor curDoctor;

    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_patient_clicked);

        Intent intent = getIntent();
        curHolder = intent.getParcelableExtra("TimeSlot Holder");

        appointmentDate = findViewById(R.id.ApptPatientDate);
        appointmentStartTime = findViewById(R.id.ApptPatientTime);
        appointmentEndTime = findViewById(R.id.ApptPatientEndTime);
        doctorName = findViewById(R.id.ApptPatientDoctorName);
        doctorUsername = findViewById(R.id.ApptPatientDoctorEmail);
        doctorPhoneNumber = findViewById(R.id.ApptPatientDoctorPhoneNumber);
        ratingBar = findViewById(R.id.doctorRatingBar);

        appointmentDate.setText("Appointment Date: " + curHolder.getAppointmentDate());
        appointmentStartTime.setText("Appointment Start Time: " + curHolder.getAppointmentStartTime());
        appointmentEndTime.setText("Appointment End Time: " + curHolder.getAppointmentEndTime());
        doctorName.setText("Doctor Name: " + curHolder.getAppointmentDoctorName());

        Database.getDoctor(curHolder.getTimeSlot().getAppointmentDoctorEmail(), new Database.MyCallBack2() {
            @Override
            public void onCallBack2(Doctor p) {
                if (p == null) {
                    //Might have to add more precautions here in case the patient gets deleted
                    //after the appointment has already been created. Otherwise this should never occur
                    System.out.println("Doctor is not in database.");
                }

                else {
                    //MOVED HERE.... WAITS FOR DATABASE TO RETRIEVE INFO
                    System.out.println("P: " + p);
                    curDoctor = p;
                    doctorUsername.setText("Doctor Email: " + curDoctor.getUsername());
                    doctorPhoneNumber.setText("Doctor Phone Number: " + curDoctor.getPhoneNumber());
                }
            }
        });
        ratingBar.setRating(curHolder.getRating());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Database.changePatientAppointmentRating(curHolder.getTimeSlot(), v);
                curHolder.getTimeSlot().setRating(v);

            }
        });

        if (curHolder.getTimeSlot().isUpcoming()) {
            ratingBar.setVisibility(View.GONE);
            findViewById(R.id.rateDoctor).setVisibility(View.GONE);
        }

        else {
            ratingBar.setVisibility(View.VISIBLE);
            findViewById(R.id.rateDoctor).setVisibility(View.VISIBLE);
        }
    }

    public void onClickClkdPastApptPtntRtrnButton(View view) {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickCancelAppointmentButton(View view) {
        if (cancellationIsPossible()) {
            TimeSlot delPatientAppointment = new TimeSlot(curHolder.getTimeSlot().getAppointmentDoctorEmail(),
                    curHolder.getAppointmentDate() + " " +
                            curHolder.getAppointmentStartTime() + " " +
                            curHolder.getAppointmentEndTime(),
                    curHolder.getTimeSlot().getTimeSlotSpecialty(), curHolder.getTimeSlot().getStatus(),
                    curHolder.getTimeSlot().getRating());

            //removing appointment
            ((Patient) Database.currentUser).removePatientAppointment(delPatientAppointment);

            //updating patient database
            (new Handler()).post(new Runnable() {
                @Override
                public void run() {
                    Database.deletePatientAppointment(delPatientAppointment);
                }
            });

            //changing the format of the date and time to match the Appointment class
            String formatChange[] = curHolder.getTimeSlot().getDateAndTimeString().split(" ");
            String startDateAndTimeString = formatChange[0] + " " + formatChange[1];

            //getting the doctor associated with the appointment
            Database.getDoctorWithID(curHolder.getTimeSlot().getAppointmentDoctorEmail(), new Database.MyCallBack3() {
                @Override
                public void onCallBack3(Doctor p, String doctorID) {
                    //deleting the appointment
                    Appointment delDoctorAppointment = new Appointment(((Patient) Database.currentUser).
                            getUsername(), startDateAndTimeString);
                    p.removeAppointment(delDoctorAppointment);
                    //updating doctor database
                    (new Handler()).post(new Runnable() {
                        @Override
                        public void run() {
                            Database.deleteDoctorAppointment(delDoctorAppointment, doctorID);
                        }
                    });
                }
            });

            Database.getAllPatients(new Database.AllPatientsCallBack() {
                @Override
                public void onAllPatientsCallBack(ArrayList<Patient> patients, ArrayList<String> patientIDs) {
                    for (String patientID : patientIDs) {
                        Database.changeTimeSlotStatus(curHolder.getTimeSlot(),
                                TimeSlot.UNBOOKED_APPOINTMENT, patientID);
                    }
                }
            });

            Toast.makeText(AppointmentClickedPatient.this, "Canceled!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(AppointmentClickedPatient.this, "Cancellation failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean cancellationIsPossible() {
        String[] p = curHolder.getTimeSlot().getDateAndTimeString().split(" ");
        String date = p[0];
        String startTime = p[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        String dateTimeString = date + " " + startTime;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        LocalDateTime now = LocalDateTime.now();
        if(dateTime.isAfter(now)) {
            long minutesDifference = java.time.Duration.between(now, dateTime).toMinutes();
            System.out.println(minutesDifference);
            return (minutesDifference > 60);
        }
        return false;
    }
}
