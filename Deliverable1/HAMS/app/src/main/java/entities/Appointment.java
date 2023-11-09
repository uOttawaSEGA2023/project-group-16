package entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment implements Parcelable {
    public static final int APPROVED_APPOINTMENT = 1;
    public static final int PENDING_APPOINTMENT = 0;
    public static final int REJECTED_APPOINTMENT = -1;

    Patient appointmentPatient;

    String appointmentPatientEmail;
    //This should be in the following format: "2023/11/07 14:28"
    //The space is important
    String startDateAndTimeString;

    Date startDateAndTime;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyyHH:mm");

    boolean upcoming;
    int status;


    public Appointment(String appointmentPatientEmail, String startDateAndTimeString) {
        this.appointmentPatientEmail = appointmentPatientEmail;
        this.startDateAndTimeString = startDateAndTimeString;
        status = PENDING_APPOINTMENT;

        //ADD A METHOD HERE THAT GETS THE PATIENT FROM A METHOD IN DATABASE THAT FINDS THE PATIENT USING THE EMAIL

        try {
            startDateAndTime = sdf.parse(startDateAndTimeString);
        }

        catch (ParseException e) {
            //MAYBE DO SOMETHING HERE, BUT SHOULDN'T HAVE TO BECAUSE WE WILL FORCE USERS TO ENTER
            //THE INFORMATION IN THE CORRECT FORMAT SO THIS SHOULDN'T BE NECESSARY
        }

        checkIfPast();
    }

    public Appointment(String appointmentPatientEmail, String startDateAndTimeString, int status) {
        this.appointmentPatientEmail = appointmentPatientEmail;
        this.startDateAndTimeString = startDateAndTimeString;
        this.status = status;

        //ADD A METHOD HERE THAT GETS THE PATIENT FROM A METHOD IN DATABASE THAT FINDS THE PATIENT USING THE EMAIL

        try {
            startDateAndTime = sdf.parse(startDateAndTimeString);
        }

        catch (ParseException e) {
            //MAYBE DO SOMETHING HERE, BUT SHOULDN'T HAVE TO BECAUSE WE WILL FORCE USERS TO ENTER
            //THE INFORMATION IN THE CORRECT FORMAT SO THIS SHOULDN'T BE NECESSARY
        }

        checkIfPast();
    }

    protected Appointment(Parcel in) {
        appointmentPatientEmail = in.readString();
        startDateAndTimeString = in.readString();
        status = in.readInt();
        appointmentPatient = in.readParcelable(Patient.class.getClassLoader());

        try {
            startDateAndTime = sdf.parse(startDateAndTimeString);
        }

        catch (ParseException e) {
            //MAYBE DO SOMETHING HERE, BUT SHOULDN'T HAVE TO BECAUSE WE WILL FORCE USERS TO ENTER
            //THE INFORMATION IN THE CORRECT FORMAT SO THIS SHOULDN'T BE NECESSARY
        }

        checkIfPast();

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
        parcel.writeInt(status);
        parcel.writeParcelable(appointmentPatient, i);
    }

    public void checkIfPast() {
        Date currentTime = new Date();

        if (currentTime.after(startDateAndTime)) {
            upcoming = false;
        }

        else {
            upcoming = true;
        }
    }

    public Patient getAppointmentPatient() {
        return appointmentPatient;
    }

    public String getAppointmentPatientEmail() {
        return appointmentPatientEmail;
    }

    public String getStartDateAndTimeString() {
        return startDateAndTimeString;
    }

    public Date getStartDateAndTime() {
        return startDateAndTime;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public boolean isUpcoming() {
        return upcoming;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        if (status != APPROVED_APPOINTMENT && status != PENDING_APPOINTMENT && status != REJECTED_APPOINTMENT) {
            //THIS SHOULD NEVER BE REACHED SO I WASN'T SURE WHAT TO PUT HERE, BUT I STILL FELT IT WAS NECESSARY
            System.out.println("Not a valid status");
        }

        else {
            this.status = status;
        }

    }
}
