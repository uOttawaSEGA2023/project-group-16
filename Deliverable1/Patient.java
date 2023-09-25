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
	
	public int getHealthCardNumber() {
		return this.healthCardNumber;
	}
	
	
}