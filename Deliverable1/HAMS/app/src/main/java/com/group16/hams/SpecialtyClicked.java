package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.group16.hams.doctor.RecyclerViewAdapterAppointment;
import com.group16.hams.doctor.RecyclerViewHolderAppointment;
import com.group16.hams.patient.SpecialtyHolder;
import com.group16.hams.patient.TimeSlotAdapter;
import com.group16.hams.patient.TimeSlotHolder;

import java.sql.Time;
import java.util.ArrayList;

import entities.Appointment;
import entities.Doctor;
import entities.Patient;
import entities.TimeSlot;

public class SpecialtyClicked extends AppCompatActivity implements RecyclerViewInterface {

    TimeSlotHolder curHolder;
    ArrayList<TimeSlotHolder> timeSlotHolders = new ArrayList<>();
    ArrayList<TimeSlot> timeSlots;

    public TimeSlotAdapter timeSlotAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_clicked);

        Intent intent = getIntent();
        curHolder = intent.getParcelableExtra("Specialty Holder");

        RecyclerView availableView = findViewById(R.id.availableTimeSlotsView);

        timeSlotAdapter = new
                TimeSlotAdapter(this, timeSlotHolders, this);


        availableView.setAdapter(timeSlotAdapter);
        availableView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onPause() {
        super.onPause();
        refresh();
    }
    @Override
    public void onStart() {
        super.onStart();
        timeSlotHolders.clear();
        setUpTimeSlotHolders();
        refresh();
    }
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void onClickTimeSlotsListReturnButton(View view) {
        finish();
    }
    public void setUpTimeSlotHolders() {
        ArrayList<TimeSlot> timeSlots1 = ((Patient) Database.currentUser).getTimeSlots();
        System.out.println(timeSlots1.size());
        TimeSlot curTimeSlot;

        String[] DateAndTime;
        String doctorName;

        for (int i = 0; i < timeSlots1.size(); i++) {
            curTimeSlot = timeSlots1.get(i);

            DateAndTime = curTimeSlot.getDateAndTimeString().split(" ");
            doctorName = curTimeSlot.getAppointmentDoctor().getFirstName() + " " +
                    curTimeSlot.getAppointmentDoctor().getLastName();

            timeSlotHolders.add(new TimeSlotHolder(DateAndTime[0],
                        DateAndTime[1], DateAndTime[2], doctorName, curTimeSlot));
        }

    }

    @Override
    public void onItemClick(int type, int position) {
        TimeSlotHolder curHolder;

        curHolder = timeSlotHolders.get(position);

        Intent intent = new Intent(this, TimeSlotClicked.class);

        intent.putExtra("TimeSlot Holder", curHolder);
        intent.putExtra("index",position);

        startActivity(intent);
    }

    public void onClickTest(View view){
        ((Patient) Database.currentUser).addTimeSlot(new TimeSlot("bobrob@gmail.com", "2021/02/23 3:30 4:00"));

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Database.timeSlotToDatabase(((Patient) Database.currentUser).getTimeSlots());
            }
        },1000);

        finish();
    }
    public void refresh(){
        timeSlotAdapter.notifyDataSetChanged();
    }

}