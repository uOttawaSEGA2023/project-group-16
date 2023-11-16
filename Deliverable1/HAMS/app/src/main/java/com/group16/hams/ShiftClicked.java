package com.group16.hams;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.group16.hams.doctor.AddShift;
import com.group16.hams.doctor.RecyclerViewHolderShift;
import com.group16.hams.doctor.ShiftsDoctor;

import entities.Doctor;
import entities.Patient;
import entities.Shift;

public class ShiftClicked extends AppCompatActivity {
    RecyclerViewHolderShift curHolder;
    int index;
    TextView shiftDate, shiftStartTime, shiftEndTime;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.shift_clicked);



        Intent intent = getIntent();
        curHolder = intent.getParcelableExtra("Shift Holder");
        index = intent.getIntExtra("index",-1);


        shiftDate = findViewById(R.id.shiftDate);
        shiftStartTime = findViewById(R.id.shiftStartTime);
        shiftEndTime = findViewById(R.id.shiftEndTime);


        shiftDate.setText("Shift Date: " + curHolder.getShiftDate());
        shiftStartTime.setText("Shift Start Time: " + curHolder.getShiftStartTime());
        shiftEndTime.setText("Shift End Time: " + curHolder.getShiftEndTime());
    }

    public void onClickDeleteShiftButton(View view){
        // Delete it from database
        Shift delShift = new Shift(curHolder.getShiftDate(), curHolder.getShiftStartTime(), curHolder.getShiftEndTime());
        ((Doctor) Database.currentUser).removeShift(delShift);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Database.deleteShift(delShift);
            }
        },1000);

        finish();
    }
}
