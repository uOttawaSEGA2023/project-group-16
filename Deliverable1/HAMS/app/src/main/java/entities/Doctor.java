package entities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import com.group16.hams.*;

public class Doctor extends User{
	
	private int employeeNumber;
	private String specialties;

	private ArrayList<Shift> shifts;

	public Doctor(String firstName, 
				String lastName, 
				String username, 
				String password, 
				String phoneNumber, 
				String address,
				int employeeNumber,
				String specialties,
				ArrayList<Shift> shifts) {
		super(firstName, lastName, username, password, phoneNumber, address);
		this.employeeNumber = employeeNumber;
		this.specialties = specialties;
		this.shifts = shifts;
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

	public ArrayList<Shift> getShifts() {
		return this.shifts;
	}

	public void setShifts(ArrayList<Shift> newShifts) {shifts = newShifts; }

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

}