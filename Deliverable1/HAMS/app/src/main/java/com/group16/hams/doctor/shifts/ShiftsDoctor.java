package com.group16.hams.doctor.shifts;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group16.hams.Database;
import com.group16.hams.R;
import com.group16.hams.RecyclerViewInterface;

import java.util.ArrayList;

import entities.Doctor;
import entities.Shift;

public class ShiftsDoctor extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<RecyclerViewHolderShift> upcomingShiftsHolders = new ArrayList<>();
    ArrayList<Shift> shifts;

    public RecyclerViewAdapterShift upcomingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shifts_doctor);

        RecyclerView shiftsView = findViewById(R.id.upcomingShiftsView);

        upcomingAdapter = new RecyclerViewAdapterShift(this, upcomingShiftsHolders, this);

        shiftsView.setAdapter(upcomingAdapter);
        shiftsView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void refresh(){
        upcomingAdapter.notifyDataSetChanged();
    }
    @Override
    public void onPause() {
        super.onPause();
        System.out.println("Paused..");
        refresh();
    }
    @Override
    public void onStart() {
        super.onStart();
        upcomingShiftsHolders.clear();
        setUpShiftHolders();
        refresh();
        System.out.println("Starting..");
    }
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setUpShiftHolders() {
        ArrayList<Shift> shifts = ((Doctor) Database.currentUser).getShifts();
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
                Shift delShift = new Shift(curShift.getDate(), curShift.getStartTime(), curShift.getEndTime());
                shiftsToDelete.add(delShift);
            }
        }
//        for (Shift delShift : shiftsToDelete) {
//            ((Doctor) Database.currentUser).removeShift(delShift);
//            (new Handler()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Database.deleteShift(delShift);
//                }
//            }, 1000);
//        }
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
