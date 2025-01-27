package es.udc.tfg.tfgprojectbackend.model.exceptions;

/**
 * IncorrectLoginException is a custom exception that is thrown when the user
 * tries to log in with an incorrect username or password.
 */

@SuppressWarnings("serial")
public class IncorrectLoginException extends Exception {
	
	private String userName;
	private String password;

	public IncorrectLoginException(String userName, String password) {
		
		this.userName = userName;
		this.password = password;
		
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
}
