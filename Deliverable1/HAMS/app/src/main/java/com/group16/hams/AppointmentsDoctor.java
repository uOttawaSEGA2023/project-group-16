package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import entities.*;

public class AppointmentsDoctor extends AppCompatActivity {

    ArrayList<RecyclerViewHolderAppointment> upcomingAppointmentHolders = new ArrayList<>();
    ArrayList<RecyclerViewHolderAppointment> pastAppointmentHolders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointments_doctor);

        RecyclerView upcomingView = findViewById(R.id.upcomingAppointmentsView);
        RecyclerView pastView = findViewById(R.id.pastAppointmentsView);

        setUpUpcomingAppointmentHolders();
        setUpPastAppointmentHolders();

        RecyclerViewAdapterAppointment upcomingAdapter = new
                RecyclerViewAdapterAppointment(this, upcomingAppointmentHolders);
        RecyclerViewAdapterAppointment pastAdapter = new
                RecyclerViewAdapterAppointment(this, pastAppointmentHolders);

        upcomingView.setAdapter(upcomingAdapter);
        pastView.setAdapter(pastAdapter);
        upcomingView.setLayoutManager(new LinearLayoutManager(this));
        pastView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onClickAppointmentsListReturnButton(View view) {
        finish();
    }

    private void setUpUpcomingAppointmentHolders() {
        //WILL NEED TO FINISH THIS PROPERLY ONCE THE APPOINTMENT CLASS IS COMPLETE
    }

    private void setUpPastAppointmentHolders() {
        //WILL NEED TO FINISH THIS PROPERLY ONCE THE APPOINTMENT CLASS IS COMPLETE
    }


}