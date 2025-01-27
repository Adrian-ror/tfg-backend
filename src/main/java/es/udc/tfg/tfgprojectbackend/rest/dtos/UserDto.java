package es.udc.tfg.tfgprojectbackend.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A DTO class for the User entity.
 *
 * This class is used to transfer user data between the application and the client.
 */
public class UserDto {

	/**
	 * A group of constraints that are used to validate the user data when creating a new user.
	 */
	public interface AllValidations {}

	/**
	 * A group of constraints that are used to validate the user data when updating an existing user.
	 */
	public interface UpdateValidations {}

	private Long id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String image;
	private String role;
	private String status;

	public UserDto() {}

	/**
	 * Constructs a UserDto object with the given user data.
	 *
	 * @param id          the user's id
	 * @param userName    the user's username
	 * @param firstName   the user's first name
	 * @param lastName    the user's last name
	 * @param email       the user's email
	 * @param phoneNumber the user's phone number
	 * @param image       the user's profile image
	 * @param role        the user's role
	 * @param status      the user's status
	 */
	public UserDto(Long id, String userName, String firstName, String lastName,
				   String email, String phoneNumber, String image, String role, String status) {
		this.id = id;
		this.userName = userName != null ? userName.trim() : null;
		this.firstName = firstName.trim();
		this.lastName = lastName.trim();
		this.email = email.trim();
		this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : null;
		this.image = image;
		this.role = role;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull(groups={AllValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class})
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName != null ? userName.trim() : null;
	}

	@NotNull(groups={AllValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class})
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.trim();
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	@Email(groups={AllValidations.class, UpdateValidations.class})
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : null;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
