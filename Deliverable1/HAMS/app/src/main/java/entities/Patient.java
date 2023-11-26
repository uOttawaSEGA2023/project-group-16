package entities;

import android.os.Parcel;

import androidx.annotation.NonNull;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;

public class Patient extends User{
	
	private int healthCardNumber;
	private ArrayList<TimeSlot> timeSlots;
	private ArrayList<TimeSlot> patientAppointments;
	
	public Patient(String firstName, 
				String lastName, 
				String username, 
				String password, 
				String phoneNumber, 
				String address, 
				int healthCardNumber) {
		super(firstName, lastName, username, password, phoneNumber, address);
		this.healthCardNumber = healthCardNumber;

		timeSlots = new ArrayList<>();
		patientAppointments = new ArrayList<>();
	}

	public Patient(String firstName,
				   String lastName,
				   String username,
				   String password,
				   String phoneNumber,
				   String address,
				   int healthCardNumber,
				   ArrayList<TimeSlot> timeSlots, ArrayList<TimeSlot> patientAppointments) {
		super(firstName, lastName, username, password, phoneNumber, address);
		this.healthCardNumber = healthCardNumber;
		this.timeSlots = timeSlots;
		this.patientAppointments = patientAppointments;
	}

	protected Patient(Parcel in) {
		super(in);
		healthCardNumber = in.readInt();
		timeSlots = in.readArrayList(null);
		patientAppointments = in.readArrayList(null);
	}

	public int getHealthCardNumber() {
		return this.healthCardNumber;
	}

	public void setHealthCardNumber(int newHealthCardNumber) {
		healthCardNumber = newHealthCardNumber;
	}

	public ArrayList<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	public ArrayList<TimeSlot> getPatientAppointments() { return patientAppointments; }
	public void addPatientAppointments(TimeSlot newPatientAppointment) { patientAppointments.add(newPatientAppointment); }

	public void addTimeSlot(TimeSlot newTimeSlot) {
		timeSlots.add(newTimeSlot);
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
		parcel.writeList(timeSlots);
		parcel.writeList(patientAppointments);
	}
	
}