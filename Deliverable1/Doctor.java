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
	
	public int getEmployeeNumber() {
		return this.employeeNumber;
	}

	public void setEmployeeNumber(int newEmployeeNumber) {
		employeeNumber = newEmployeeNumber;
	} 
	
	public String getSpecialties() {
		String result = "";
		for (int i = 0; i < specialties.length; i++) {
			result += specialties[i] + ", ";
		}
		
		return result.substring(0,specialties.length-2);
		
	}
	
	
	
	
}