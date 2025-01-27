package es.udc.tfg.tfgprojectbackend.rest.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object (DTO) to change the password of a user.
 *
 */
public class ChangePasswordParamsDto {
	
	private String oldPassword;
	private String newPassword;
	
	public ChangePasswordParamsDto() {}

	//@NotNull annotation is used to validate that the field is not null
	@NotNull
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	//The @Size annotation is used to validate that the field is between the specified size
	@NotNull
	@Size(min=1, max=60)
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
