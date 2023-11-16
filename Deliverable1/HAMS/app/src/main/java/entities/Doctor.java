package entities;

import android.os.Build;
import android.os.Parcel;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Doctor extends User{

	// Instance Variables
	private int employeeNumber;
	private String specialties;
	private ArrayList<Appointment> appointments;
	private ArrayList<Shift> shifts;
	private boolean autoApprove;


	// Constructors with 4 variants
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

		appointments = new ArrayList<Appointment>();
		autoApprove = false;
		shifts = new ArrayList<Shift>();
	}

	public Doctor(String firstName, String lastName, String username, String password, String phoneNumber, String address, int employeeNumber, String specialties,
				  ArrayList<Appointment> appointments,
				  boolean autoApprove) {
		this(firstName, lastName, username, password, phoneNumber, address, employeeNumber, specialties);
		this.appointments = appointments;
		this.autoApprove = autoApprove;
	}

	public Doctor(String firstName, String lastName, String username, String password, String phoneNumber, String address, int employeeNumber, String specialties, ArrayList<Appointment> appointments, boolean autoApprove,
				  ArrayList<Shift> shifts) {
		this(firstName, lastName, username, password, phoneNumber, address, employeeNumber, specialties, appointments, autoApprove);
		this.shifts = shifts;
	}

	protected Doctor(Parcel in) {
		super(in);
		employeeNumber = in.readInt();
		specialties = in.readString();
		appointments = in.readArrayList(null);
		autoApprove = in.readByte() != 0;
		shifts = in.readArrayList(null);
	}

	// Instance Methods
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

	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	public void addAppointment(Appointment newAppointment) {
		if (autoApprove) {
			newAppointment.setStatus(Appointment.APPROVED_APPOINTMENT);
		}
		appointments.add(newAppointment);
	}

	public boolean getAutoApprove() {
		return autoApprove;
	}

	public void setAutoApprove(boolean autoApprove) {
		this.autoApprove = autoApprove;
	}

	public ArrayList<Shift> getShifts() {
		return shifts;
	}

	public void addShift(Shift newShift) {
		shifts.add(newShift);
	}

	public void removeShift(Shift delShift){
		shifts.remove(delShift);
	}

	@Override
	public String toString() {
		return "---Doctor---\n" + super.toString() +
				"\nEmployee #: " + employeeNumber +
				"\nSpecialties: " + specialties +
				"";
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
		parcel.writeList(appointments);
		parcel.writeByte((byte) (autoApprove ? 1 : 0));
		parcel.writeList(shifts);
	}
}