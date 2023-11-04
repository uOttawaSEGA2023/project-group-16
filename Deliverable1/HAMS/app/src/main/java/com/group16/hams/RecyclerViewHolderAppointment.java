package com.group16.hams;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RecyclerViewHolderAppointment implements Parcelable {

    public static final int PAST_APPOINTMENT = 0;
    public static final int UPCOMING_APPOINTMENT = 1;
    String appointmentDate;
    String appointmentTime;
    String appointmentPatientName;
    String appointmentApproval;
    int pastOrUpcoming;

    public RecyclerViewHolderAppointment(String appointmentDate, String appointmentTime,
                                         String appointmentPatientName,
                                         String appointmentApproval) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentPatientName = appointmentPatientName;
        this.appointmentApproval = appointmentApproval;
    }

    protected RecyclerViewHolderAppointment(Parcel in) {
        appointmentDate = in.readString();
        appointmentTime = in.readString();
        appointmentPatientName = in.readString();
        appointmentApproval = in.readString();
        pastOrUpcoming = in.readInt();
    }

    public static final Creator<RecyclerViewHolderAppointment> CREATOR = new Creator<RecyclerViewHolderAppointment>() {
        @Override
        public RecyclerViewHolderAppointment createFromParcel(Parcel in) {
            return new RecyclerViewHolderAppointment(in);
        }

        @Override
        public RecyclerViewHolderAppointment[] newArray(int size) {
            return new RecyclerViewHolderAppointment[size];
        }
    };

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getAppointmentPatientName() {
        return appointmentPatientName;
    }

    public String getAppointmentApproval() {
        return appointmentApproval;
    }

    public int getPastOrUpcoming() {
        return pastOrUpcoming;
    }

    public void setPastOrUpcoming(int pastOrUpcoming) {
        this.pastOrUpcoming = pastOrUpcoming;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(appointmentDate);
        parcel.writeString(appointmentTime);
        parcel.writeString(appointmentPatientName);
        parcel.writeString(appointmentApproval);
        parcel.writeInt(pastOrUpcoming);

    }
}
