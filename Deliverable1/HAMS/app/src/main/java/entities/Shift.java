package entities;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.group16.hams.Database;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Shift implements Parcelable {
    private String date;
    private String startTime;
    private String endTime;
    private String doctorUsername;
    private String doctorSpecialty;


    public Shift(String date, String startTime, String endTime, String doctorUsername, String doctorSpecialty){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctorUsername = doctorUsername;
        this.doctorSpecialty = doctorSpecialty;
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isFuture() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateTimeString = date + " " + startTime;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        LocalDateTime now = LocalDateTime.now();
        return dateTime.isAfter(now);
    }

    @Override
    public String toString() {
        return "Shift{" +
                "date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
    }

    protected Shift(Parcel in) {
        date = in.readString();
        startTime = in.readString();
        endTime = in.readString();

    }

    public static final Creator<Shift> CREATOR = new Creator<Shift>() {
        @Override
        public Shift createFromParcel(Parcel in) {
            return new Shift(in);
        }

        @Override
        public Shift[] newArray(int size) {
            return new Shift[size];
        }
    };

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public String getDoctorSpecialty() {
        return doctorSpecialty;
    }
}
