package es.udc.tfg.tfgprojectbackend.rest.dtos;

import jakarta.validation.constraints.NotNull;

/**
 * Data transfer object (DTO) for a login request.
 *
 * This class is used to receive the parameters of a login request.
 */
public class LoginParamsDto {
	
	private String userName;
	private String password;
	
	public LoginParamsDto() {}

	//@NotNull is used to validate that the userName field is not null.
	@NotNull
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.trim();
	}

	@NotNull
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
