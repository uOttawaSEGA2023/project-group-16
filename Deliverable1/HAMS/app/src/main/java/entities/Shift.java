package entities;

import androidx.appcompat.app.AppCompatActivity;

public class Shift extends AppCompatActivity {
    private String date;
    private String startTime;
    private String endTime;

    public Shift(String date, String startTime, String endTime){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDate(){
        return date;
    }

    public String getStartTime(){
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
}
