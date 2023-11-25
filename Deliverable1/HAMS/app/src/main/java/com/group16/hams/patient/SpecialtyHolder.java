package com.group16.hams.patient;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import entities.Specialty;

public class SpecialtyHolder implements Parcelable {

    String specialtyName;
    Specialty specialty;

    public SpecialtyHolder(String specialtyName, Specialty specialty) {
        this.specialtyName = specialtyName;
        this.specialty = specialty;
    }

    protected SpecialtyHolder(Parcel in) {
        specialtyName = in.readString();
        specialty = in.readParcelable(Specialty.class.getClassLoader());
    }

    public static final Creator<SpecialtyHolder> CREATOR = new Creator<SpecialtyHolder>() {
        @Override
        public SpecialtyHolder createFromParcel(Parcel in) {
            return new SpecialtyHolder(in);
        }

        @Override
        public SpecialtyHolder[] newArray(int size) {
            return new SpecialtyHolder[size];
        }
    };

    public String getSpecialtyName() {
        return specialtyName;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(specialtyName);
        parcel.writeParcelable(specialty, i);
    }
}
