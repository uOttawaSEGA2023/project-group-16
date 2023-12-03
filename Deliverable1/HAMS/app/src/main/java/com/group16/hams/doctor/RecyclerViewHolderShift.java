package com.group16.hams.doctor;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import entities.Shift;

public class RecyclerViewHolderShift implements Parcelable{

    public static final int PAST_SHIFT = 0;
    public static final int UPCOMING_SHIFT = 1;
    String shiftDate;
    String shiftStartTime;

    String shiftEndTime;
    int pastOrUpcoming;

    Shift shift;
    public RecyclerViewHolderShift(String shiftDate, String shiftStartTime,
                                         String shiftEndTime,
                                         int pastOrUpcoming,
                                         Shift shift) {
        this.shiftDate = shiftDate;
        this.shiftStartTime = shiftStartTime;
        this.shiftEndTime = shiftEndTime;
        this.pastOrUpcoming = pastOrUpcoming;
        this.shift = shift;
    }

    protected RecyclerViewHolderShift(Parcel in) {
        shiftDate = in.readString();
        shiftStartTime = in.readString();
        shiftEndTime = in.readString();
        pastOrUpcoming = in.readInt();
        shift = in.readParcelable(Shift.class.getClassLoader());
    }

    public static final Parcelable.Creator<RecyclerViewHolderShift> CREATOR = new Parcelable.Creator<RecyclerViewHolderShift>() {
        @Override
        public RecyclerViewHolderShift createFromParcel(Parcel in) {
            return new RecyclerViewHolderShift(in);
        }

        @Override
        public RecyclerViewHolderShift[] newArray(int size) {
            return new RecyclerViewHolderShift[size];
        }
    };

    public String getShiftDate() {
        return shiftDate;
    }

    public String getShiftStartTime() {
        return shiftStartTime;
    }

    public String getShiftEndTime() {
        return shiftEndTime;
    }

    public int getPastOrUpcoming() {
        return pastOrUpcoming;
    }

    public Shift getShift() {
        return shift;
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
        parcel.writeString(shiftDate);
        parcel.writeString(shiftStartTime);
        parcel.writeString(shiftEndTime);
        parcel.writeInt(pastOrUpcoming);
        parcel.writeParcelable(shift, i);
    }
}
