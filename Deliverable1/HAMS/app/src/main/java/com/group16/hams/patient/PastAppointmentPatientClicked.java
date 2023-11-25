package com.group16.hams.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.group16.hams.Database;
import com.group16.hams.R;

import entities.Doctor;

public class PastAppointmentPatientClicked extends AppCompatActivity {

    TimeSlotHolder curHolder;

    TextView appointmentDate, appointmentStartTime, appointmentEndTime, doctorName, doctorUsername,
            doctorPhoneNumber;

    Doctor curDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_appointment_patient_clicked);

        Intent intent = getIntent();
        curHolder = intent.getParcelableExtra("TimeSlot Holder");

        appointmentDate = findViewById(R.id.pastApptPatientDate);
        appointmentStartTime = findViewById(R.id.pastApptPatientTime);
        appointmentEndTime = findViewById(R.id.pastApptPatientEndTime);
        doctorName = findViewById(R.id.pastApptPatientDoctorName);
        doctorUsername = findViewById(R.id.pastApptPatientDoctorEmail);
        doctorPhoneNumber = findViewById(R.id.pastApptPatientDoctorPhoneNumber);

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
    }

    public void onClickClkdPastApptPtntRtrnButton(View view) {
        finish();
    }
}