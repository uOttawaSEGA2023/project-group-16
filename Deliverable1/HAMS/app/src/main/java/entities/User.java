package entities;
public class User{
	
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
}
