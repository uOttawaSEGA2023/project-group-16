package com.group16.hams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.group16.hams.doctor.RecyclerViewHolderShift;

import entities.Patient;

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
        finish();
    }
}
