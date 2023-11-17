package com.group16.hams.doctor;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.group16.hams.Database;
import com.group16.hams.R;
import com.group16.hams.register.RegisterDoctor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Appointment;
import entities.Doctor;
import entities.Shift;

public class AddShift extends AppCompatActivity {

    private EditText addShiftDate, addShiftStartTime, addShiftEndTime;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shift);

        addShiftDate = findViewById(R.id.addShiftDate);
        addShiftStartTime = findViewById(R.id.addShiftStartTime);
        addShiftEndTime = findViewById(R.id.addShiftEndTime);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSubmitShiftButton(View view){
        boolean validFlag = true;
        String addShiftDateText = addShiftDate.getText().toString();
        String addShiftStartTimeText = addShiftStartTime.getText().toString();
        String addShiftEndTimeText = addShiftEndTime.getText().toString();

        if (validateDateFormat(addShiftDateText) == false) {
            addShiftDate.setError("Invalid Date Format (Date/Month/YYYY)");
            validFlag = false;
        }
        else{
            String[] parts = addShiftDateText.split("/");
            if (parts[0].length() == 1){
                parts[0] = "0" + parts[0];
            }
            if (parts[1].length() == 1){
                parts[1] = "0" + parts[1];
            }
            addShiftDateText = parts[0] + "/" + parts[1] + "/" + parts[2];
        }


        if (validateTimeFormat(addShiftStartTimeText) == false){
            addShiftStartTime.setError("Invalid Time Format (HH:MM)");
            validFlag = false;
        }

        if (validateTimeFormat(addShiftEndTimeText) == false){
            addShiftEndTime.setError("Invalid Time Format (HH:MM)");
            validFlag = false;
        }
        if (validateDateFormat(addShiftDateText) == true && validateTimeFormat(addShiftStartTimeText) == true && validateTimeFormat(addShiftEndTimeText) == true){
            if (validateStartingTime(addShiftDateText, addShiftStartTimeText) == false){
                addShiftDate.setError("Expired Date or Start Time");
                addShiftStartTime.setError("Expired Date or Start Time");
                validFlag = false;
            }
            else{
                if (validateTimeIncrements(addShiftStartTimeText, addShiftEndTimeText) == false){
                    addShiftStartTime.setError("Not 30min Increment");
                    addShiftEndTime.setError("Not 30min Increment");
                    validFlag = false;
                }
                else{
                    if (isShiftConflict(addShiftDateText, addShiftStartTimeText, addShiftEndTimeText) == true){
                        addShiftStartTime.setError("Conflicting Time");
                        addShiftEndTime.setError("Conflicting Time");
                        validFlag = false;
                    }
                }
            }
        }
        if (validFlag == true){
            // Add the shift to the doctor's DB
            ((Doctor) Database.currentUser).addShift(new Shift(addShiftDateText, addShiftStartTimeText, addShiftEndTimeText));
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Database.shiftToDatabase(((Doctor) Database.currentUser).getShifts());
                }
            },1000);

            Toast.makeText(AddShift.this, "Success!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validateDateFormat(String addShiftDateText){
        try{
            String[] addShiftDateTextSplit = addShiftDateText.split("/");
            String day = addShiftDateTextSplit[0];
            String month = addShiftDateTextSplit[1];
            String year = addShiftDateTextSplit[2];

            if (addShiftDateTextSplit.length != 3 || year.length() != 4){
                return false;
            }

            for (int i = 0; i < addShiftDateTextSplit.length; i++){
                if (addShiftDateTextSplit[i].isEmpty() == true){
                    return false;
                }
            }

            if (day.length() == 1){
                day = "0" + day;
            }
            if (month.length() == 1){
                month = "0" + month;
            }

            addShiftDateText = day + "/" + month + "/" + year;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate givenDate = LocalDate.parse(addShiftDateText, formatter);
            if (!givenDate.format(formatter).equals(addShiftDateText)) {
                return false;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean validateTimeFormat(String time){
        String regex = "([01][0-9]|2[0-3]):[0-5][0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);

        return matcher.matches();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validateStartingTime(String date, String startTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateTimeString = date + " " + startTime;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        LocalDateTime now = LocalDateTime.now();
        return dateTime.isAfter(now);
    }

    public boolean validateTimeIncrements(String startTime, String endTime){
        String[] startTimeParts = startTime.split(":");
        String[] endTimeParts = endTime.split(":");
        int startHours = Integer.parseInt(startTimeParts[0]);
        int startMinutes = Integer.parseInt(startTimeParts[1]);
        int convertedStartMinutes = startHours * 60 + startMinutes;
        int endHours = Integer.parseInt(endTimeParts[0]);
        int endMinutes = Integer.parseInt(endTimeParts[1]);
        int convertedEndMinutes = endHours * 60 + endMinutes;
        if (convertedStartMinutes >= convertedEndMinutes){
            return false;
        }
        return convertedStartMinutes % 30 == 0 && convertedEndMinutes % 30 == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isShiftConflict(String date, String startTime, String endTime){
        ArrayList<Shift> existingShifts = ((Doctor) Database.currentUser).getShifts();
        Shift curShift;
        LocalTime newShiftStart = LocalTime.parse(startTime);
        LocalTime newShiftEnd = LocalTime.parse(endTime);

        for (int i = 0; i < existingShifts.size(); i++) {
            curShift = existingShifts.get(i);
            if (curShift.getDate().equals(date)){

                LocalTime curShiftStart = LocalTime.parse(curShift.getStartTime());
                LocalTime curShiftEnd = LocalTime.parse(curShift.getEndTime());

                if ((newShiftStart.isBefore(curShiftEnd) && newShiftStart.isAfter(curShiftStart)) ||
                        (newShiftEnd.isAfter(curShiftStart) && newShiftEnd.isBefore(curShiftEnd)) ||
                        (newShiftStart.equals(curShiftStart) || newShiftEnd.equals(curShiftEnd))) {
                    return true;
                }
            }

        }
        return false;
    }
}
