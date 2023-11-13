package com.group16.hams.doctor;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.group16.hams.R;
import com.group16.hams.register.RegisterDoctor;

public class AddShift extends AppCompatActivity {

    private EditText addShiftDate, addShiftStartTime, addShiftEndTime;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shift);

        addShiftDate = findViewById(R.id.addShiftDate);
        addShiftStartTime = findViewById(R.id.addShiftStartTime);
        addShiftEndTime = findViewById(R.id.addShiftEndTime);
    }

    public void onClickSubmitShiftButton(View view){
        boolean validFlag = true;
        String addShiftDateText = addShiftDate.getText().toString();
        String addShiftStartTimeText = addShiftStartTime.getText().toString();
        String addShiftEndTimeText = addShiftEndTime.getText().toString();
        Toast.makeText(AddShift.this, addShiftDateText, Toast.LENGTH_SHORT).show();

        // Add the shift to the doctor's DB


    }

    public boolean validateDate(String addShiftDateText){
        String[] addShiftDateTextSplit = addShiftDateText.split("/");

        if (addShiftDateTextSplit.length != 3){
            return false;
        }

        String day = addShiftDateTextSplit[0];
        String month = addShiftDateTextSplit[1];
        String year = addShiftDateTextSplit[2];



        return true;
    }
}
