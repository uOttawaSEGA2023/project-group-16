package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

public class Shift extends AppCompatActivity {
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;

    public Shift(String startDate, String startTime, String endDate, String endTime, String assignedTo){
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getStartTime(){
        return startTime;
    }
    public String getEndDate(){
        return endDate;
    }
    public String getEndTime(){
        return endTime;
    }
}
