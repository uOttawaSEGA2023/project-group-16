package com.group16.hams.patient;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Time;

import entities.*;

public class TimeSlotHolder implements Parcelable {

    String appointmentDate;
    String appointmentStartTime;
    String appointmentEndTime;
    String appointmentDoctorName;

    TimeSlot timeSlot;

    public TimeSlotHolder(String appointmentDate, String appointmentStartTime,
                                         String appointmentEndTime,
                                         String appointmentDoctorName,
                                         TimeSlot timeSlot) {
        this.appointmentDate = appointmentDate;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
        this.appointmentDoctorName = appointmentDoctorName;
        this.timeSlot = timeSlot;
    }

    protected TimeSlotHolder(Parcel in) {
        appointmentDate = in.readString();
        appointmentStartTime = in.readString();
        appointmentEndTime = in.readString();
        appointmentDoctorName = in.readString();
        timeSlot = in.readParcelable(TimeSlot.class.getClassLoader());
    }

    public static final Creator<com.group16.hams.patient.TimeSlotHolder> CREATOR = new Creator<com.group16.hams.patient.TimeSlotHolder>() {
        @Override
        public com.group16.hams.patient.TimeSlotHolder createFromParcel(Parcel in) {
            return new com.group16.hams.patient.TimeSlotHolder(in);
        }

        @Override
        public com.group16.hams.patient.TimeSlotHolder[] newArray(int size) {
            return new com.group16.hams.patient.TimeSlotHolder[size];
        }
    };

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public String getAppointmentDoctorName() {
        return appointmentDoctorName;
    }

    public String getAppointmentBooking() {
        if (timeSlot.getStatus() == TimeSlot.BOOKED_APPOINTMENT) {
            return "Booked";
        }
        else {
            return "Unbooked";
        }
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public float getRating(){
        return this.timeSlot.getRating();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(appointmentDate);
        parcel.writeString(appointmentStartTime);
        parcel.writeString(appointmentEndTime);
        parcel.writeString(appointmentDoctorName);
        parcel.writeParcelable(timeSlot, i);

    }
}
