package entities;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class Patient extends User{
	
	private int healthCardNumber;
	
	public Patient(String firstName, 
				String lastName, 
				String username, 
				String password, 
				String phoneNumber, 
				String address, 
				int healthCardNumber) {
		super(firstName, lastName, username, password, phoneNumber, address);
		this.healthCardNumber = healthCardNumber;
	}

	protected Patient(Parcel in) {
		super(in);
		healthCardNumber = in.readInt();
	}

	public int getHealthCardNumber() {
		return this.healthCardNumber;
	}

	public void setHealthCardNumber(int newHealthCardNumber) {
		healthCardNumber = newHealthCardNumber;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(firstName);
		parcel.writeString(lastName);
		parcel.writeString(username);
		parcel.writeString(password);
		parcel.writeString(phoneNumber);
		parcel.writeString(address);
		parcel.writeInt(healthCardNumber);
	}
	
}