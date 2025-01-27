package es.udc.tfg.tfgprojectbackend.rest.controllers;

import es.udc.tfg.tfgprojectbackend.model.entities.PaymentMethod;
import es.udc.tfg.tfgprojectbackend.model.entities.Product;
import es.udc.tfg.tfgprojectbackend.model.entities.User;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import es.udc.tfg.tfgprojectbackend.model.services.SellerService;
import es.udc.tfg.tfgprojectbackend.model.services.UserService;
import es.udc.tfg.tfgprojectbackend.rest.common.ErrorsDto;
import es.udc.tfg.tfgprojectbackend.rest.common.JwtGenerator;
import es.udc.tfg.tfgprojectbackend.rest.common.JwtInfo;
import es.udc.tfg.tfgprojectbackend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;

import static es.udc.tfg.tfgprojectbackend.rest.dtos.UserConversor.*;


/**
 * Controller to manage user operations.
 */
// @RestController annotation indicates that the class serves the role of a controller.
// @RequestMapping annotation is used to map web requests to specific handler classes and/or handler methods.
@RestController
@RequestMapping("/users")
public class UserController {

	/**
	 * Code for the exception when the login is incorrect.
	 */
	private final static String INCORRECT_LOGIN_EXCEPTION_CODE = "project.exceptions.IncorrectLoginException";

	/**
	 * Code for the exception when the password is incorrect.
	 */
	private final static String INCORRECT_PASSWORD_EXCEPTION_CODE = "project.exceptions.IncorrectPasswordException";


	/**
	 * Code for the exception when the user is banned.
	 */
	private final static String USER_BANNED_EXCEPTION_CODE = "project.exceptions.UserBannedException";

	/**
	 * MessageSource object to get messages from the properties files.
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * JwtGenerator object to generate the service token.
	 */
	@Autowired
	private JwtGenerator jwtGenerator;

	/**
	 * UserService object to manage user operations.
	 */
	@Autowired
	private UserService userService;


	/**
	 * Method to handle the IncorrectLoginException exception.
	 * @param exception IncorrectLoginException object.
	 * @param locale Locale object.
	 * @return ErrorsDto object with the error message.
	 */
	@ExceptionHandler(IncorrectLoginException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleIncorrectLoginException(IncorrectLoginException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(INCORRECT_LOGIN_EXCEPTION_CODE, null,
				INCORRECT_LOGIN_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}

	/**
	 * Method to handle the IncorrectPasswordException exception.
	 * @param exception IncorrectPasswordException object.
	 * @param locale Locale object.
	 * @return ErrorsDto object with the error message.
	 */
	@ExceptionHandler(IncorrectPasswordException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleIncorrectPasswordException(IncorrectPasswordException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(INCORRECT_PASSWORD_EXCEPTION_CODE, null,
				INCORRECT_PASSWORD_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}


	/**
	 * Method to handle the UserBannedException exception.
	 * @param exception UserBannedException object.
	 * @param locale Locale object.
	 * @return ErrorsDto object with the error message.
	 */
	@ExceptionHandler(UserBannedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorsDto handleUserBannedException(UserBannedException exception, Locale locale) {

		String errorMessage = messageSource.getMessage(USER_BANNED_EXCEPTION_CODE, null,
				USER_BANNED_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
	}



	/**
	 * Method to sign up a new user.
	 * @param userDto UserDto object with the user data.
	 * @return ResponseEntity object with the authenticated user data.
	 * @throws DuplicateInstanceException if the user already exists.
	 */
// @Validated annotation is used to validate the input request.
	@PostMapping("/signUp")
	public ResponseEntity<AuthenticatedUserDto> signUp(
			@RequestBody UserDto userDto) throws DuplicateInstanceException {

		User user = toUser(userDto);

		userService.signUp(user);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getId()).toUri();

		return ResponseEntity.created(location)
				.body(toAuthenticatedUserDto(generateServiceToken(user), user));
	}


	/**
	 * Method to log in a user.
	 * @param params LoginParamsDto object with the user data.
	 * @return AuthenticatedUserDto object with the authenticated user data.
	 * @throws IncorrectLoginException if the login is incorrect.
	 */
	@PostMapping("/login")
	public AuthenticatedUserDto login(@Validated @RequestBody LoginParamsDto params)
            throws IncorrectLoginException, UserBannedException {
		
		User user = userService.login(params.getUserName(), params.getPassword());

		return toAuthenticatedUserDto(generateServiceToken(user), user);
		
	}

	/**
	 * Method to log in a user from the service token.
	 * @param userId Long with the user id.
	 * @param serviceToken String with the service token.
	 * @return AuthenticatedUserDto object with the authenticated user data.
	 * @throws InstanceNotFoundException if the user does not exist.
	 */
	@PostMapping("/loginFromServiceToken")
	public AuthenticatedUserDto loginFromServiceToken(@RequestAttribute Long userId, 
		@RequestAttribute String serviceToken) throws InstanceNotFoundException {
		
		User user = userService.loginFromId(userId);
		
		return toAuthenticatedUserDto(serviceToken, user);
		
	}

	/**
	 * Method to update the user profile.
	 * @param userId Long with the user id.
	 * @param id Long with the user id.
	 * @param userDto UserDto object with the user data.
	 * @return UserDto object with the updated user data.
	 * @throws InstanceNotFoundException if the user does not exist.
	 * @throws PermissionException if the user does not have permission.
	 */
	@PutMapping("/{id}")
	public UserDto updateProfile(
			@RequestAttribute Long userId,
			@PathVariable Long id,
			@Validated(UserDto.UpdateValidations.class)
			@RequestBody UserDto userDto) throws InstanceNotFoundException, PermissionException {

		if (!id.equals(userId)) {
			throw new PermissionException();
		}

		return toUserDto(userService.updateProfile(id, userDto.getFirstName(),
				userDto.getLastName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getImage()));
	}


	/**
	 * Method to change the user password.
	 * @param userId Long with the user id.
	 * @param id Long with the user id.
	 * @param params ChangePasswordParamsDto object with the password data.
	 * @throws PermissionException if the user does not have permission.
	 * @throws InstanceNotFoundException if the user does not exist.
	 * @throws IncorrectPasswordException if the password is incorrect.
	 */
	@PostMapping("/{id}/changePassword")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(@RequestAttribute Long userId, @PathVariable Long id,
		@Validated @RequestBody ChangePasswordParamsDto params)
		throws PermissionException, InstanceNotFoundException, IncorrectPasswordException {
		
		if (!id.equals(userId)) {
			throw new PermissionException();
		}
		
		userService.changePassword(id, params.getOldPassword(), params.getNewPassword());
		
	}


	@PostMapping("/{id}/ban")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void banUser(@PathVariable Long id) throws InstanceNotFoundException {
		userService.banUser(id);
	}

	@PostMapping("/{id}/unban")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unbanUser(@PathVariable Long id) throws InstanceNotFoundException {
		userService.unbanUser(id);
	}

	@GetMapping("/list")
	public List<UserDto> findAllUsers() {
		return toUserDtos(userService.findAllUsers());
	}

	@GetMapping("/active")
	public List<UserDto> findAllActiveUsers() {
		return toUserDtos(userService.findAllActiveUsers());
	}

	@GetMapping("/banned")
	public List<UserDto> findAllBannedUsers() {
		return toUserDtos(userService.findAllBannedUsers());
	}


	/**
	 * Method to generate the service token.
	 * @param user User object with the user data.
	 * @return String with the service token.
	 */
	private String generateServiceToken(User user) {
		
		JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getUserName(), user.getRole().toString());
		
		return jwtGenerator.generate(jwtInfo);
		
	}
	
}
