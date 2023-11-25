package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.group16.hams.doctor.RecyclerViewHolderAppointment;
import com.group16.hams.patient.TimeSlotHolder;

import entities.Appointment;
import entities.Doctor;
import entities.Patient;
import entities.TimeSlot;

public class TimeSlotClicked extends AppCompatActivity {

    TimeSlotHolder curHolder;
    int index;

    TextView appointmentDate, appointmentStartTime, appointmentEndTime, curBookingStatus, doctorName, doctorUsername,
            doctorPhoneNumber;
    Doctor curDoctor;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_slot_clicked);

        Intent intent = getIntent();
        curHolder = intent.getParcelableExtra("TimeSlot Holder");
        index = intent.getIntExtra("index",-1);

        this.email = curHolder.getTimeSlot().getAppointmentDoctorEmail();

        Database.getDoctor(email, new Database.MyCallBack2() {
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
                    doctorUsername.setText("Doctor Username: " + curDoctor.getUsername());
                    doctorPhoneNumber.setText("Doctor Phone Number: " + curDoctor.getPhoneNumber());
                }
            }
        });

        appointmentDate = findViewById(R.id.appointmentDate);
        appointmentStartTime = findViewById(R.id.appointmentStartTime);
        appointmentEndTime = findViewById(R.id.appointmentEndTime);
        doctorName = findViewById(R.id.doctorName);
        doctorUsername = findViewById(R.id.doctorUsername);
        doctorPhoneNumber = findViewById(R.id.doctorPhoneNumber);
        curBookingStatus = findViewById(R.id.currentBookingStatus);

        appointmentDate.setText("Appointment Date: " + curHolder.getAppointmentDate());
        appointmentStartTime.setText("Appointment Start Time: " + curHolder.getAppointmentStartTime());
        appointmentEndTime.setText("Appointment End Time: " + curHolder.getAppointmentEndTime());
        curBookingStatus.setText("Current Booking Status: " + curHolder.getAppointmentBooking());
        doctorName.setText("Doctor Name: " + curHolder.getAppointmentDoctorName());
    }
    public void onClickBookAppointmentButton(View view) {

        if (curHolder.getTimeSlot().getStatus() == TimeSlot.BOOKED_APPOINTMENT) {
            Toast t = new Toast(TimeSlotClicked.this);
            t.makeText(TimeSlotClicked.this, "Appointment had already been booked",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            curHolder.getTimeSlot().setStatus(TimeSlot.BOOKED_APPOINTMENT);
            curBookingStatus = findViewById(R.id.currentApprovalStatus);
            curBookingStatus.setText("Current Booking Status: " + curHolder.getAppointmentBooking());
            Database.changeTimeSlotStatus(curHolder.getTimeSlot(), TimeSlot.BOOKED_APPOINTMENT);
        }
    }
    public void onClickReturnToAppointmentsButton(View view) {
        finish();
    }
}