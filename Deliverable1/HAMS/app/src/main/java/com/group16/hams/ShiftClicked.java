package com.group16.hams;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.group16.hams.doctor.AddShift;
import com.group16.hams.doctor.RecyclerViewHolderShift;
import com.group16.hams.doctor.ShiftsDoctor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entities.Appointment;
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
        ArrayList<Appointment> appointments = ((Doctor) Database.currentUser).getAppointments();
        Shift delShift = curHolder.getShift();

        boolean overlap = false;

        for (Appointment appointment : appointments) {
            if (isOverlapping(delShift, appointment)) {
                System.out.println("OVERLAPPING WORKING");
                overlap = true;
                break;
            }
        }

        if (!overlap) {
            ((Doctor) Database.currentUser).removeShift(delShift);
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Database.deleteShift(delShift);
                }
            },1000);
        }

        else {
            Toast toast = new Toast(ShiftClicked.this);
            toast.makeText(ShiftClicked.this,
                    "Cannot delete shift because there is an appointment during it",
                    Toast.LENGTH_LONG).show();
        }

        finish();
    }

    private boolean isOverlapping (Shift shift, Appointment appointment) {
        String shiftDate = shift.getDate();
        String shiftStart = shift.getStartTime();
        String shiftEnd = shift.getEndTime();

        //THIS IS NOT WORKING
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/ddHH:mm");

        Date shiftStartDateAndTime = null;
        Date shiftEndDateAndTime = null;

        try {
            shiftStartDateAndTime = sdf.parse(shiftDate + shiftStart);
            shiftEndDateAndTime = sdf.parse(shiftDate + shiftEnd);
        } catch (ParseException e) {
            //Shouldn't have to do anything here because
        }

        System.out.println(shiftStartDateAndTime);
        System.out.println(shiftEndDateAndTime);

        Date appointmentDate = appointment.getStartDateAndTime();

        /*
        String[] appointmentDateTime = appointment.getStartDateAndTimeString().split(" ");
        String appointmentDate = appointmentDateTime[0];
        String appointmentStartTime = appointmentDateTime[1];
        String appointmentEndTime = addMinutes(appointmentStartTime, 30);

        //THIS DOES NOT WORK
        int intShiftStart = Integer.parseInt(shiftStart);
        int intShiftEnd = Integer.parseInt(shiftEnd);
        int intApptStart = Integer.parseInt(appointmentStartTime);
        int intApptEnd = Integer.parseInt(appointmentEndTime);
         */

        if ((appointmentDate.compareTo(shiftStartDateAndTime) >= 0) &&
                (appointmentDate.compareTo(shiftEndDateAndTime) < 0)) {
            return true;
        }

        else {
            return false;
        }
    }

    public void onClickShiftClickedReturn(View view) {
        finish();
    }

}
