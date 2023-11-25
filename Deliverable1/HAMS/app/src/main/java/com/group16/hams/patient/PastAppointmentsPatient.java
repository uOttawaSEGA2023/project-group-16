package com.group16.hams.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.group16.hams.Database;
import com.group16.hams.R;
import com.group16.hams.RecyclerViewInterface;

import java.util.ArrayList;

import entities.Patient;
import entities.TimeSlot;

public class PastAppointmentsPatient extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<TimeSlotHolder> timeSlotHolders = new ArrayList<>();

    public TimeSlotAdapter timeSlotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_appointments_patient);

        RecyclerView pastAppointmentsRecycler = findViewById(R.id.pastAppointmentsPatientView);

        timeSlotAdapter = new
                TimeSlotAdapter(this, timeSlotHolders, this);

        pastAppointmentsRecycler.setAdapter(timeSlotAdapter);
        pastAppointmentsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setUpPastTimeSlotHolders() {
        ArrayList<TimeSlot> allTimeSlots = ((Patient) Database.currentUser).getTimeSlots();
        TimeSlot curTimeSlot;

        String[] DateAndTime;
        String doctorName;

        for (int i = 0; i < allTimeSlots.size(); i++) {
            curTimeSlot = allTimeSlots.get(i);

            if ((curTimeSlot.getStatus() == TimeSlot.BOOKED_APPOINTMENT) && (curTimeSlot.isUpcoming())) {
                DateAndTime = curTimeSlot.getDateAndTimeString().split(" ");
                doctorName = curTimeSlot.getAppointmentDoctor().getFirstName() + " " +
                        curTimeSlot.getAppointmentDoctor().getLastName();

                timeSlotHolders.add(new TimeSlotHolder(DateAndTime[0],
                        DateAndTime[1], DateAndTime[2], doctorName, curTimeSlot));
            }
        }
    }

    public void onClickPastAppointmentsPatientReturn(View view){
        finish();
    }

    @Override
    public void onItemClick(int type, int position) {
        TimeSlotHolder curHolder = timeSlotHolders.get(position);

        Intent intent = new Intent(this, PastAppointmentPatientClicked.class);
        intent.putExtra("TimeSlot Holder", curHolder);
        startActivity(intent);
    }
}