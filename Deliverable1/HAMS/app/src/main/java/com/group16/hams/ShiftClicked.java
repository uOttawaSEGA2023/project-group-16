package com.group16.hams;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.group16.hams.doctor.AddShift;
import com.group16.hams.doctor.RecyclerViewHolderShift;
import com.group16.hams.doctor.ShiftsDoctor;
import com.group16.hams.patient.AppointmentClickedPatient;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entities.Appointment;
import entities.Doctor;
import entities.Patient;
import entities.Shift;
import entities.TimeSlot;

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
            (new Handler()).post(new Runnable() {
                @Override
                public void run() {
                    Database.deleteShift(delShift);
                }
            });

            String[] p = delShift.getDate().split("/");
            String changeDateFormat = p[2] + "/" + p[1] + "/" + p[0];
            ArrayList<TimeSlot> temp = new ArrayList<>();
            ArrayList<String> tempIDs = new ArrayList<>();
            Database.getAllPatients(new Database.AllPatientsCallBack() {
                @Override
                public void onAllPatientsCallBack(ArrayList<Patient> patients, ArrayList<String> patientIDs) {
                    for (String patientID : patientIDs) {
                        tempIDs.add(patientID);
                        int numberOfTimeSlots = AddShift.calculateNumberOfTimeSlots(delShift.getStartTime(), delShift.getEndTime());
                        int timeSlotInterval = 30;
                        int shiftStartMinute = AddShift.convertTimeToMinutes(delShift.getStartTime());
                        for (int k = 0; k < numberOfTimeSlots; k++) {
                            int startTime = shiftStartMinute + k * timeSlotInterval;
                            int endTime = startTime + timeSlotInterval;

                            String timeSlotStartTime = AddShift.convertMinutesToTime(startTime);
                            String timeSlotEndTime = AddShift.convertMinutesToTime(endTime);
                            String thisDoctorEmail = ((Doctor) Database.currentUser).getUsername();
                            String thisDoctorSpecialty = ((Doctor) Database.currentUser).getSpecialties();

                            temp.add(new TimeSlot(thisDoctorEmail, changeDateFormat + " "
                                    + timeSlotStartTime + " " + timeSlotEndTime, thisDoctorSpecialty));
                        }
                    }
                    for (String patientID : tempIDs) {
                        for (TimeSlot t : temp) {
                            Database.changeTimeSlotStatus(t,
                                    TimeSlot.BOOKED_APPOINTMENT, patientID);
                        }
                    }
                }
            });

            Toast.makeText(ShiftClicked.this, "Canceled!", Toast.LENGTH_SHORT).show();
            finish();
        }

        else {
            Toast toast = new Toast(ShiftClicked.this);
            toast.makeText(ShiftClicked.this,
                    "Cannot delete shift because there is an appointment during it",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean isOverlapping (Shift shift, Appointment appointment) {
        String shiftDate = shift.getDate();
        String shiftStart = shift.getStartTime();
        String shiftEnd = shift.getEndTime();

        //THIS IS NOT WORKING
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyyHH:mm");

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
