package es.udc.tfg.tfgprojectbackend.rest.common;

import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This class is used to handle common exceptions thrown by the application.
 */

//@ControllerAdvice is an annotation that allows to handle exceptions
// across the whole application in one global handling component.
@ControllerAdvice
public class CommonControllerAdvice {

	/**
	 * The following constants are used to define the error codes for the exceptions.
	 */
	private final static String INSTANCE_NOT_FOUND_EXCEPTION_CODE = "project.exceptions.InstanceNotFoundException";
	private final static String DUPLICATE_INSTANCE_EXCEPTION_CODE = "project.exceptions.DuplicateInstanceException";
	private final static String PERMISSION_EXCEPTION_CODE = "project.exceptions.PermissionException";

	/**
	 * The following attribute is used to access the messages from the message.properties file.
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * This method is used to handle the MethodArgumentNotValidException exception.
	 * @param exception The exception to be handled.
	 * @return An ErrorsDto object with the error messages.
	 */

	// @ExceptionHandler is an annotation used to define the exception type to be handled.
	// @ResponseStatus is an annotation used to define the HTTP status code to be returned.
	// @ResponseBody is an annotation used to define the return type of the method.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorsDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
				
		List<FieldErrorDto> fieldErrors = exception.getBindingResult().getFieldErrors().stream()
			.map(error -> new FieldErrorDto(error.getField(), error.getDefaultMessage())).collect(Collectors.toList());
		
		return new ErrorsDto(fieldErrors);
	    
	}

	/**
	 * This method is used to handle the InstanceNotFoundException exception.
	 * @param exception The exception to be handled.
	 * @param locale The locale to be used to get the messages.
	 * @return An ErrorsDto object with the error message.
	 */

	// @ExceptionHandler is an annotation used to define the exception type to be handled.
	// @ResponseStatus is an annotation used to define the HTTP status code to be returned.
	// @ResponseBody is an annotation used to define the return type of the method.
	@ExceptionHandler(InstanceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleInstanceNotFoundException(InstanceNotFoundException exception, Locale locale) {
		
		String nameMessage = messageSource.getMessage(exception.getName(), null, exception.getName(), locale);
		String errorMessage = messageSource.getMessage(INSTANCE_NOT_FOUND_EXCEPTION_CODE, 
				new Object[] {nameMessage, exception.getKey().toString()}, INSTANCE_NOT_FOUND_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}

	/**
	 * This method is used to handle the DuplicateInstanceException exception.
	 * @param exception The exception to be handled.
	 * @param locale The locale to be used to get the messages.
	 * @return An ErrorsDto object with the error message.
	 */

	// @ExceptionHandler is an annotation used to define the exception type to be handled.
	// @ResponseStatus is an annotation used to define the HTTP status code to be returned.
	// @ResponseBody is an annotation used to define the return type of the method.
	@ExceptionHandler(DuplicateInstanceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorsDto handleDuplicateInstanceException(DuplicateInstanceException exception, Locale locale) {
		
		String nameMessage = messageSource.getMessage(exception.getName(), null, exception.getName(), locale);
		String errorMessage = messageSource.getMessage(DUPLICATE_INSTANCE_EXCEPTION_CODE, 
				new Object[] {nameMessage, exception.getKey().toString()}, DUPLICATE_INSTANCE_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}

	/**
	 * This method is used to handle the PermissionException exception.
	 * @param exception The exception to be handled.
	 * @param locale The locale to be used to get the messages.
	 * @return An ErrorsDto object with the error message.
	 */
	// @ExceptionHandler is an annotation used to define the exception type to be handled.
	// @ResponseStatus is an annotation used to define the HTTP status code to be returned.
	// @ResponseBody is an annotation used to define the return type of the method.
	@ExceptionHandler(PermissionException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorsDto handlePermissionException(PermissionException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(PERMISSION_EXCEPTION_CODE, null, PERMISSION_EXCEPTION_CODE,
			locale);

		return new ErrorsDto(errorMessage);
		
	}

}
