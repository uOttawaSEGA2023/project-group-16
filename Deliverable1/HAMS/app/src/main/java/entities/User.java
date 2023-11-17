package entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {
	
	//Instance Variables******************************
	protected String firstName;
	protected String lastName;
	protected String username;
	protected String password;
	protected String phoneNumber;
	protected String address;
	
	//Constructor*************************************
	public User(String firstName, 
				String lastName, 
				String username, 
				String password, 
				String phoneNumber, 
				String address) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.username = username;
			this.password = password;
			this.phoneNumber = phoneNumber;
			this.address = address;
		}
	
	//Instance Methods********************************

	protected User(Parcel in) {
		firstName = in.readString();
		lastName = in.readString();
		username = in.readString();
		password = in.readString();
		phoneNumber = in.readString();
		address = in.readString();
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	// Getters //
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}
	
	// Setters //
	
	protected void setUsername(String newUsername) {
		this.username = newUsername;
	}
	
	protected void setPassword(String newPassword) {
		this.password = newPassword;
	}
	
	protected void setPhoneNumber(String newNumber) {
		this.phoneNumber = newNumber;
	}
	
	protected void setAddress(String newAddress) {
		this.address = newAddress;
	}
	
	/**
	 * Returns basic information of a User. 
	 * 
	 * @returns A String with information on a user's name, address, phone number.
	 */
	public String toString() {
		return "Name: " 
				+ getFirstName() 
				+ " " + getLastName() 
				+ "\nPhone number: "
				+ getPhoneNumber()
				+ "\nAddress: "
				+ getAddress();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(firstName);
		parcel.writeString(lastName);
		parcel.writeString(username);
		parcel.writeString(password);
		parcel.writeString(phoneNumber);
		parcel.writeString(address);
	}
}
