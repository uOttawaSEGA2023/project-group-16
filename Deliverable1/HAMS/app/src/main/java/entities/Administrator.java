package entities;

public class Administrator extends User{

	/**
	 * Pre-made Administrator user with predefined username and password
	 */
	public Administrator() {
		super("", "", "admin@admin.com", "adminadmin", "", "");
	}
}