package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.group16.hams.patient.TimeSlotAdapter;
import com.group16.hams.patient.TimeSlotHolder;

import java.util.ArrayList;

import entities.Patient;
import entities.TimeSlot;

public class SpecialtyClicked extends AppCompatActivity implements RecyclerViewInterface {

    String curHolder;
    ArrayList<TimeSlotHolder> timeSlotHolders = new ArrayList<>();
    ArrayList<TimeSlot> timeSlots;

    public TimeSlotAdapter timeSlotAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_clicked);

        Intent intent = getIntent();
        curHolder = intent.getStringExtra("Specialty Holder");

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

            if (curTimeSlot.getTimeSlotSpecialty() != null && curTimeSlot.getTimeSlotSpecialty().equals(curHolder) && curTimeSlot.getStatus() != TimeSlot.BOOKED_APPOINTMENT) {
                DateAndTime = curTimeSlot.getDateAndTimeString().split(" ");
                doctorName = curTimeSlot.getAppointmentDoctor().getFirstName() + " " +
                        curTimeSlot.getAppointmentDoctor().getLastName();

                timeSlotHolders.add(new TimeSlotHolder(DateAndTime[0],
                        DateAndTime[1], DateAndTime[2], doctorName, curTimeSlot));
            }
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
    public void refresh(){
        timeSlotAdapter.notifyDataSetChanged();
    }

}