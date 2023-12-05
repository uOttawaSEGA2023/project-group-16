package com.group16.hams.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.group16.hams.AppointmentClickedDoctor;
import com.group16.hams.Database;
import com.group16.hams.R;
import com.group16.hams.RecyclerViewInterface;

import java.util.ArrayList;

import entities.*;

public class AppointmentsDoctor extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<RecyclerViewHolderAppointmentDoctor> upcomingAppointmentHolders = new ArrayList<>();
    ArrayList<RecyclerViewHolderAppointmentDoctor> pastAppointmentHolders = new ArrayList<>();
    ArrayList<Appointment> appointments;

    public RecyclerViewAdapterAppointmentDoctor upcomingAdapter;
    public RecyclerViewAdapterAppointmentDoctor pastAdapter;

    boolean autoApprove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointments_doctor);

        System.out.println("BEING CREATED!!!!!!!!!!!!");

        RecyclerView upcomingView = findViewById(R.id.upcomingAppointmentsView);
        RecyclerView pastView = findViewById(R.id.pastAppointmentsView);

        upcomingAdapter = new
                RecyclerViewAdapterAppointmentDoctor(this, upcomingAppointmentHolders, this);
        pastAdapter = new
                RecyclerViewAdapterAppointmentDoctor(this, pastAppointmentHolders, this);


        upcomingView.setAdapter(upcomingAdapter);
        pastView.setAdapter(pastAdapter);
        upcomingView.setLayoutManager(new LinearLayoutManager(this));
        pastView.setLayoutManager(new LinearLayoutManager(this));

        Button autoApproveButton = findViewById(R.id.autoApproveButton);
        this.autoApprove = ((Doctor) Database.currentUser).getAutoApprove();

        if (autoApprove) {
            autoApproveButton.setText("Disable Auto Approve");
        }

        else {
            autoApproveButton.setText("Enable Auto Approve");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        refresh();
    }
    @Override
    public void onStart() {
        super.onStart();
        upcomingAppointmentHolders.clear();
        pastAppointmentHolders.clear();
        setUpAppointmentHolders();
        refresh();
    }
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void onClickAppointmentsListReturnButton(View view) {
        finish();
    }

    public void onClickAutoApprove(View view) {
        Button autoApproveButton = findViewById(R.id.autoApproveButton);
        System.out.println("Is auto approve on? " + ((Doctor)Database.currentUser).getAutoApprove());
        if (autoApprove) {
            autoApproveButton.setText("Enable Auto Approve");
            Database.changeAutoApprove(false);
            System.out.println("Changing to false..");
            System.out.println("GetAutoApprove: = " + ((Doctor)Database.currentUser).getAutoApprove());
            autoApprove = ((Doctor) Database.currentUser).getAutoApprove();
        } else {
            autoApproveButton.setText("Disable Auto Approve");
            Database.changeAutoApprove(true);

            System.out.println("GetAutoApprove: = " + ((Doctor)Database.currentUser).getAutoApprove());
            ArrayList < Appointment > existingAppointments = ((Doctor) Database.currentUser).getAppointments();
            for (int i = 0; i < existingAppointments.size(); i++) {
                if (existingAppointments.get(i).isUpcoming()) {
                    existingAppointments.get(i).setStatus(Appointment.APPROVED_APPOINTMENT);
                }
            }
            Database.appointmentToDatabase(existingAppointments);
            autoApprove = ((Doctor) Database.currentUser).getAutoApprove();
            refresh();
        }
    }

    public void setUpAppointmentHolders() {
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
                if (autoApprove) {
                    curAppointment.setStatus(Appointment.APPROVED_APPOINTMENT);
                }

                upcomingAppointmentHolders.add(new RecyclerViewHolderAppointmentDoctor(dateAndTime[0],
                        dateAndTime[1], patientName, curAppointment.getStatus(),
                        RecyclerViewHolderAppointmentDoctor.UPCOMING_APPOINTMENT, curAppointment));
            }

            else {
                pastAppointmentHolders.add(new RecyclerViewHolderAppointmentDoctor(dateAndTime[0],
                        dateAndTime[1], patientName, curAppointment.getStatus(),
                        RecyclerViewHolderAppointmentDoctor.PAST_APPOINTMENT, curAppointment));
            }
        }

    }

    @Override
    public void onItemClick(int type, int position) {
        RecyclerViewHolderAppointmentDoctor curHolder;

        if (type == RecyclerViewHolderAppointmentDoctor.PAST_APPOINTMENT) {
            curHolder = pastAppointmentHolders.get(position);
        }

        else {
            curHolder = upcomingAppointmentHolders.get(position);
        }

        Intent intent = new Intent(this, AppointmentClickedDoctor.class);

        intent.putExtra("Appointment Holder", curHolder);
        intent.putExtra("index",position);

        startActivity(intent);
    }

    public void refresh(){
        upcomingAdapter.notifyDataSetChanged();
        pastAdapter.notifyDataSetChanged();
    }

}