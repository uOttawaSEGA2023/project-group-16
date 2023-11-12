package com.group16.hams.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.group16.hams.AppointmentClicked;
import com.group16.hams.Database;
import com.group16.hams.R;
import com.group16.hams.RecyclerViewAdapterAppointment;
import com.group16.hams.RecyclerViewHolderAppointment;
import com.group16.hams.RecyclerViewInterface;

import java.util.ArrayList;

import entities.*;

public class AppointmentsDoctor extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<RecyclerViewHolderAppointment> upcomingAppointmentHolders = new ArrayList<>();
    ArrayList<RecyclerViewHolderAppointment> pastAppointmentHolders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointments_doctor);

        RecyclerView upcomingView = findViewById(R.id.upcomingAppointmentsView);
        RecyclerView pastView = findViewById(R.id.pastAppointmentsView);

        setUpAppointmentHolders();

        RecyclerViewAdapterAppointment upcomingAdapter = new
                RecyclerViewAdapterAppointment(this, upcomingAppointmentHolders, this);
        RecyclerViewAdapterAppointment pastAdapter = new
                RecyclerViewAdapterAppointment(this, pastAppointmentHolders, this);

        upcomingView.setAdapter(upcomingAdapter);
        pastView.setAdapter(pastAdapter);
        upcomingView.setLayoutManager(new LinearLayoutManager(this));
        pastView.setLayoutManager(new LinearLayoutManager(this));

        Button autoApproveButton = findViewById(R.id.autoApproveButton);

        if (((Doctor)Database.currentUser).getAutoApprove()) {
            autoApproveButton.setText("Disable Auto Approve");
        }

        else {
            autoApproveButton.setText("Enable Auto Approve");
        }
    }

    public void onClickAppointmentsListReturnButton(View view) {
        finish();
    }

    public void onClickAutoApprove(View view) {
        Button autoApproveButton = findViewById(R.id.autoApproveButton);

        if (((Doctor)Database.currentUser).getAutoApprove()) {
            autoApproveButton.setText("Enable Auto Approve");
            ((Doctor)Database.currentUser).setAutoApprove(false);
        }

        else {
            autoApproveButton.setText("Disable Auto Approve");
            ((Doctor)Database.currentUser).setAutoApprove(true);

            ArrayList<Appointment> existingAppointments = ((Doctor)Database.currentUser).getAppointments();

            for (int i = 0; i < existingAppointments.size(); i++) {
                if (existingAppointments.get(i).isUpcoming()) {
                    existingAppointments.get(i).setStatus(Appointment.APPROVED_APPOINTMENT);
                }
            }
        }
    }

    private void setUpAppointmentHolders() {
        ArrayList<Appointment> appointments = ((Doctor)Database.currentUser).getAppointments();
        System.out.println(appointments.size());
        Appointment curAppointment;

        String[] dateAndTime;
        String patientName;

        for (int i = 0; i < appointments.size(); i++) {
            curAppointment = appointments.get(i);
            curAppointment.checkIfPast();

            dateAndTime = curAppointment.getStartDateAndTimeString().split(" ");
            patientName = curAppointment.getAppointmentPatient().getFirstName() + " " +
                    curAppointment.getAppointmentPatient().getLastName();

            if (curAppointment.isUpcoming()) {
                upcomingAppointmentHolders.add(new RecyclerViewHolderAppointment(dateAndTime[0],
                        dateAndTime[1], patientName, curAppointment.getStatus(),
                        RecyclerViewHolderAppointment.UPCOMING_APPOINTMENT, curAppointment));
            }

            else {
                pastAppointmentHolders.add(new RecyclerViewHolderAppointment(dateAndTime[0],
                        dateAndTime[1], patientName, curAppointment.getStatus(),
                        RecyclerViewHolderAppointment.PAST_APPOINTMENT, curAppointment));
            }
        }

    }

    @Override
    public void onItemClick(int type, int position) {
        RecyclerViewHolderAppointment curHolder;

        if (type == RecyclerViewHolderAppointment.PAST_APPOINTMENT) {
            curHolder = pastAppointmentHolders.get(position);
        }

        else {
            curHolder = upcomingAppointmentHolders.get(position);
        }

        Intent intent = new Intent(this, AppointmentClicked.class);

        intent.putExtra("Appointment Holder", curHolder);

        startActivity(intent);
    }


    public void onClickTest(View view){
        ((Doctor) Database.currentUser).addAppointment(new Appointment("example1@example.com", "2021/02/23 3:45"));
        ((Doctor) Database.currentUser).addAppointment(new Appointment("example1@example.com", "2022/05/10 19:27"));
        ((Doctor) Database.currentUser).addAppointment(new Appointment("example1@example.com", "2021/09/30 12:36"));
        ((Doctor) Database.currentUser).addAppointment(new Appointment("example2@example.com", "2023/11/11 4:00"));
        ((Doctor) Database.currentUser).addAppointment(new Appointment("example2@example.com", "2024/01/27 15:55"));
        ((Doctor) Database.currentUser).addAppointment(new Appointment("example2@example.com", "2025/10/01 23:00", Appointment.REJECTED_APPOINTMENT));
        ((Doctor) Database.currentUser).addAppointment(new Appointment("uaroha@gmail.com", "2024/07/07 20:12"));
        //Database.appointmentToDatabase(((Doctor) Database.currentUser).getAppointments());
    }
}