package com.group16.hams.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group16.hams.AppointmentClicked;
import com.group16.hams.R;
import com.group16.hams.RecyclerViewAdapterAppointment;
import com.group16.hams.RecyclerViewHolderAppointment;
import com.group16.hams.RecyclerViewInterface;

import java.util.ArrayList;

public class ShiftsDoctor extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<RecyclerViewHolderAppointment> shifts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shifts_doctor);

        //RecyclerView shiftsView = findViewById(R.id.upcomingShiftsView);


    }


    @Override
    public void onItemClick(int type, int position) {
        /**
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
         **/
    }

    public void onClickShiftsDoctorReturnButton(View view){
        finish();
    }

    public void onClickAddShiftButton(View view){
        Intent intent = new Intent(this, AddShift.class);
        startActivity(intent);
    }
}
