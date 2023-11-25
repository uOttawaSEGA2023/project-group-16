package entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Specialty implements Parcelable {

    private String specialty;
    public Specialty(String specialty) {
        this.specialty = specialty;
    }

    protected Specialty(Parcel in) {
        specialty = in.readString();
    }

    public static final Creator<Specialty> CREATOR = new Creator<Specialty>() {
        @Override
        public Specialty createFromParcel(Parcel in) {
            return new Specialty(in);
        }

        @Override
        public Specialty[] newArray(int size) {
            return new Specialty[size];
        }
    };

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(specialty);
    }

}
