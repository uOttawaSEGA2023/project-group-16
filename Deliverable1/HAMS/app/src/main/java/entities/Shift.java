package entities;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public boolean isFuture() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateTimeString = date + " " + startTime;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        LocalDateTime now = LocalDateTime.now();
        return dateTime.isAfter(now);
    }
}
