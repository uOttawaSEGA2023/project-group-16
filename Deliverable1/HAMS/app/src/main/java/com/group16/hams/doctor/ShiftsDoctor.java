package com.group16.hams.doctor;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group16.hams.Database;
import com.group16.hams.R;
import com.group16.hams.RecyclerViewInterface;
import com.group16.hams.ShiftClicked;

import java.util.ArrayList;

import entities.Appointment;
import entities.Doctor;
import entities.Shift;

public class ShiftsDoctor extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<RecyclerViewHolderShift> upcomingShiftsHolders = new ArrayList<>();
    ArrayList<Shift> shifts;

    public RecyclerViewAdapterShiftDoctor upcomingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shifts_doctor);

        RecyclerView shiftsView = findViewById(R.id.upcomingShiftsView);

        upcomingAdapter = new RecyclerViewAdapterShiftDoctor(this, upcomingShiftsHolders, this);

        shiftsView.setAdapter(upcomingAdapter);
        shiftsView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void refresh(){
        upcomingAdapter.notifyDataSetChanged();
    }
    @Override
    public void onPause() {
        super.onPause();
        refresh();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        upcomingShiftsHolders.clear();
        setUpAppointmentHolders();
        refresh();
    }
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setUpAppointmentHolders() {
        ArrayList<Shift> shifts = ((Doctor) Database.currentUser).getShifts();
        ArrayList<Appointment> appointments = ((Doctor) Database.currentUser).getAppointments();
        System.out.println(shifts.size());
        System.out.println("Shift: " + shifts);
        Shift curShift;
        ArrayList<Shift> shiftsToDelete = new ArrayList<>();

        for (int i = 0; i < shifts.size(); i++) {
            curShift = shifts.get(i);

            if (curShift.isFuture()) {
                upcomingShiftsHolders.add(new RecyclerViewHolderShift(curShift.getDate(),
                        curShift.getStartTime(), curShift.getEndTime(), 1, curShift));
            }
            else{
                for (Appointment appointment : appointments) {
                    if (isOverlapping(curShift, appointment)) {
                        // alert user of overlap and inability to remove appointment
                    } else {
                        Shift delShift = new Shift(curShift.getDate(), curShift.getStartTime(), curShift.getEndTime());
                        shiftsToDelete.add(delShift);
                    }

                }
            }
        }
        for (Shift delShift : shiftsToDelete) {
            ((Doctor) Database.currentUser).removeShift(delShift);
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Database.deleteShift(delShift);
                }
            }, 1000);
        }
    }

    private boolean isOverlapping (Shift shift, Appointment appointment) {
        String shiftDate = shift.getDate();
        String shiftStart = shift.getStartTime();
        String shiftEnd = shift.getEndTime();

        String[] appointmentDateTime = appointment.getStartDateAndTimeString().split(" ");
        String appointmentDate = appointmentDateTime[0];
        String appointmentStartTime = appointmentDateTime[1];
        String appointmentEndTime = addMinutes(appointmentStartTime, 30);

        int intShiftStart = Integer.parseInt(shiftStart);
        int intShiftEnd = Integer.parseInt(shiftEnd);
        int intApptStart = Integer.parseInt(appointmentStartTime);
        int intApptEnd = Integer.parseInt(appointmentEndTime);

        if (appointmentDate == shiftDate) {
            if (intShiftStart < intApptStart && intApptEnd < intShiftEnd) {
                return true;
            }
        }
        return false;
    }


    private String addMinutes(String time, Integer minutesToAdd) {
        String[] hourMin = time.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int min = Integer.parseInt(hourMin[1]);

        min += minutesToAdd;

        if (min >= 60) {
            hour += 1;
            min -= 60;
        }

        if (hour >= 24) {
            hour -= 24;
        }

        return String.format("%02d:%02d", hour, min);

    }
    @Override
    public void onItemClick(int type, int position) {
        RecyclerViewHolderShift curHolder;

        curHolder = upcomingShiftsHolders.get(position);


        Intent intent = new Intent(this, ShiftClicked.class);

        intent.putExtra("Shift Holder", curHolder);
        intent.putExtra("index",position);

        startActivity(intent);
    }

    public void onClickShiftsDoctorReturnButton(View view){
        finish();
    }

    public void onClickAddShiftButton(View view){
        Intent intent = new Intent(this, AddShift.class);
        startActivity(intent);
    }
}

