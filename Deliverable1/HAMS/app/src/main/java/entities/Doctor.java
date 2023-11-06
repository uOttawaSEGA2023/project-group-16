package entities;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class Doctor extends User{
	
	private int employeeNumber;
	private String specialties;
	
	public Doctor(String firstName, 
				String lastName, 
				String username, 
				String password, 
				String phoneNumber, 
				String address, 
				int employeeNumber,
				String specialties) {
		super(firstName, lastName, username, password, phoneNumber, address);
		this.employeeNumber = employeeNumber;
		this.specialties = specialties;
	}

	protected Doctor(Parcel in) {
		super(in);
		employeeNumber = in.readInt();
		specialties = in.readString();
	}
	
	public int getEmployeeNumber() {
		return this.employeeNumber;
	}

	public void setEmployeeNumber(int id) {
		employeeNumber = id;
	} 
	
	public String getSpecialties() {
		String[] words = specialties.trim().toLowerCase().split(",");

		StringBuilder processedString = new StringBuilder();

		for (String word : words) {
			processedString.append(word.trim()).append(", ");
		}

		if (processedString.length() > 2) {
			processedString.setLength(processedString.length() - 2);
		}

		return processedString.toString();
	}

	public void setSpecialties(String s) {
		specialties = s;
	}

	@Override
	public String toString() {
		return "Doctor{" +
				"firstName='" + getFirstName() + '\'' +
				", lastName='" + getLastName() + '\'' +
				", username='" + getUsername() + '\'' +
				", phoneNumber='" + getPhoneNumber() + '\'' +
				", address='" + getAddress() + '\'' +
				", employeeNumber=" + employeeNumber +
				", specialties='" + specialties + '\'' +
				'}';
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeString(firstName);
		parcel.writeString(lastName);
		parcel.writeString(username);
		parcel.writeString(password);
		parcel.writeString(phoneNumber);
		parcel.writeString(address);
		parcel.writeInt(employeeNumber);
		parcel.writeString(specialties);
	}
}