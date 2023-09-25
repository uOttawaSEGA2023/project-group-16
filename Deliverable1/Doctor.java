public class Doctor extends User{
	
	private int employeeNumber;
	private String [] specialties;
	
	public Doctor(String firstName, 
				String lastName, 
				String username, 
				String password, 
				String phoneNumber, 
				String address, 
				int employeeNumber,
				String [] specialties) {
		super(firstName, lastName, username, password, phoneNumber, address);
		this.employeeNumber = employeeNumber;
		this.specialties = specialties;
	}
	
	private int getEmployeeNumber() {
		return this.employeeNumber;
	}
	
	private String getSpecialties() {
		String result = "";
		for (int i = 0; i < specialties.length; i++) {
			result += specialties[i] + ", ";
		}
		
		return result.substring(0,specialties.length-2);
		
	}
	
	
	
	
}