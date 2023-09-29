public class User{
	protected String firstName;
	protected String lastName;
	protected String username;
	protected String password;
	protected String phoneNumber;
	protected String address;
	
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
	
	protected String getFirstName() {
		return this.firstName;
	}
	
	protected String getLastName() {
		return this.lastName;
	}
	
	protected String getUsername() {
		return this.username;
	}
	
	protected void setUsername(String newUsername) {
		this.username = newUsername;
	}
	
	protected String getPassword() {
		return this.password;
	}
	
	protected void setPassword(String newPassword) {
		this.password = newPassword;
	}
	
	protected String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	protected void setPhoneNumber(String newNumber) {
		this.phoneNumber = newNumber;
	}

	protected String getAddress() {
		return this.address;
	}
	
	protected void setAddress(String newAddress) {
		this.address = newAddress;
	}
}
