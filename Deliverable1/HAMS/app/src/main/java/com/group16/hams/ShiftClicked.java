package com.group16.hams;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShiftClicked extends AppCompatActivity {
    RecyclerViewHolderShift curHolder;
    int index;
    TextView shiftDate, shiftStartTime, shiftEndTime;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.shift_clicked);

        //Database.
    }
}
