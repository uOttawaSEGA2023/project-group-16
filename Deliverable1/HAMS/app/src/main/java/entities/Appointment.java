package entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Appointment implements Parcelable {
    public static final int APPROVED_APPOINTMENT = 1;
    public static final int PENDING_APPOINTMENT = 0;
    public static final int REJECTED_APPOINTMENT = -1;

    Patient appointmentPatient;

    //MIGHT NOT NEED END TIME BECAUSE IT IS ALWAYS 30 MINUTES LATER
    String appointmentPatientEmail;
    String startDateAndTimeString;
    String endDateAndTimeString;

    Date startDateAndTime;
    Date endDateAndTime;

    boolean upcoming;
    int status;


    public Appointment(String appointmentPatientEmail, String startDateAndTimeString, String endDateAndTimeString) {
        this.appointmentPatientEmail = appointmentPatientEmail;
        this.startDateAndTimeString = startDateAndTimeString;
        this.endDateAndTimeString = endDateAndTimeString;

        //ADD A METHOD HERE THAT GETS THE PATIENT FROM A METHOD IN DATABASE THAT FINDS THE PATIENT USING THE EMAIL

        //CONVERT DATE STRINGS TO ACTUAL DATES AND DETERMINE IF IT IS UPCOMING
    }

    protected Appointment(Parcel in) {
        appointmentPatientEmail = in.readString();
        startDateAndTimeString = in.readString();
        endDateAndTimeString = in.readString();
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(appointmentPatientEmail);
        parcel.writeString(startDateAndTimeString);
        parcel.writeString(endDateAndTimeString);
    }
}
