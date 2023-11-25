package entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.group16.hams.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Doctor;
import entities.Patient;

public class TimeSlot implements Parcelable {

    public static final int BOOKED_APPOINTMENT = 1;
    public static final int UNBOOKED_APPOINTMENT = 0;
    Doctor appointmentDoctor;
    String appointmentDoctorEmail;
    //This should be in the following format: "2023/11/07 14:28"
    //The space is important
    String DateAndTimeString;
    Date DateAndTime;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/ddHH:mmHH:mm");

    int status;
    public TimeSlot(String appointmentDoctorEmail, String DateAndTimeString) {
        this.appointmentDoctorEmail = appointmentDoctorEmail;
        this.DateAndTimeString = DateAndTimeString;
        status = UNBOOKED_APPOINTMENT;

        Database.getDoctor(appointmentDoctorEmail, new Database.MyCallBack2() {
            @Override
            public void onCallBack2(Doctor p) {
                if (p == null) {
                    //Might have to add more precautions here in case the patient gets deleted
                    //after the appointment has already been created. Otherwise this should never occur
                    System.out.println("Doctor is not in database.");
                }

                else {
                    appointmentDoctor = p;
                }
            }
        });

        try {
            DateAndTime = sdf.parse(DateAndTimeString);
        }

        catch (ParseException e) {
            //MAYBE DO SOMETHING HERE, BUT SHOULDN'T HAVE TO BECAUSE WE WILL FORCE USERS TO ENTER
            //THE INFORMATION IN THE CORRECT FORMAT SO THIS SHOULDN'T BE NECESSARY
        }
    }

    public TimeSlot(String appointmentDoctorEmail, String DateAndTimeString, int status) {
        this.appointmentDoctorEmail = appointmentDoctorEmail;
        this.DateAndTimeString = DateAndTimeString;
        this.status = status;

        Database.getDoctor(appointmentDoctorEmail, new Database.MyCallBack2() {
            @Override
            public void onCallBack2(Doctor p) {
                if (p == null) {
                    //Might have to add more precautions here in case the patient gets deleted
                    //after the appointment has already been created. Otherwise this should never occur
                    System.out.println("Doctor is not in database.");
                }

                else {
                    appointmentDoctor = p;
                }
            }
        });

        try {
            DateAndTime = sdf.parse(DateAndTimeString);
        }

        catch (ParseException e) {
            //MAYBE DO SOMETHING HERE, BUT SHOULDN'T HAVE TO BECAUSE WE WILL FORCE USERS TO ENTER
            //THE INFORMATION IN THE CORRECT FORMAT SO THIS SHOULDN'T BE NECESSARY
        }
    }

    protected TimeSlot(Parcel in) {
        appointmentDoctorEmail = in.readString();
        DateAndTimeString = in.readString();
        status = in.readInt();

        Database.getDoctor(appointmentDoctorEmail, new Database.MyCallBack2() {
            @Override
            public void onCallBack2(Doctor p) {
                if (p == null) {
                    //Might have to add more precautions here in case the patient gets deleted
                    //after the appointment has already been created. Otherwise this should never occur
                    System.out.println("Doctor is not in database.");
                }

                else {
                    appointmentDoctor = p;
                }
            }
        });

        try {
            DateAndTime = sdf.parse(DateAndTimeString);
        }

        catch (ParseException e) {
            //MAYBE DO SOMETHING HERE, BUT SHOULDN'T HAVE TO BECAUSE WE WILL FORCE USERS TO ENTER
            //THE INFORMATION IN THE CORRECT FORMAT SO THIS SHOULDN'T BE NECESSARY
        }
    }

    public static final Creator<entities.TimeSlot> CREATOR = new Creator<entities.TimeSlot>() {
        @Override
        public entities.TimeSlot createFromParcel(Parcel in) {
            return new entities.TimeSlot(in);
        }

        @Override
        public entities.TimeSlot[] newArray(int size) {
            return new entities.TimeSlot[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(appointmentDoctorEmail);
        parcel.writeString(DateAndTimeString);
        parcel.writeInt(status);
    }

    public Doctor getAppointmentDoctor() {
        return appointmentDoctor;
    }
    public String getAppointmentDoctorEmail() {
        return appointmentDoctorEmail;
    }
    public Date getDateAndTime() {
        return DateAndTime;
    }
    public String getDateAndTimeString() {
        return DateAndTimeString;
    }
    public SimpleDateFormat getSdf() {
        return sdf;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        if (status != BOOKED_APPOINTMENT && status != UNBOOKED_APPOINTMENT) {
            System.out.println("Not a valid status");
        }
        else {
            this.status = status;
        }
    }
}