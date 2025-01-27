package es.udc.tfg.tfgprojectbackend.rest.common;

import java.util.List;

/**
 * This class represents the errors that can be returned by the backend to the frontend.
 * It can be a global error or a list of field errors.
 *
 * @see FieldErrorDto
 */
public class ErrorsDto {
	
	private String globalError;
	private List<FieldErrorDto> fieldErrors;


	public ErrorsDto(String globalError) {
		this.globalError = globalError;
	}
	
	public ErrorsDto(List<FieldErrorDto> fieldErrors) {

		this.fieldErrors = fieldErrors;
		
	}

	public String getGlobalError() {
		return globalError;
	}

	public void setGlobalError(String globalError) {
		this.globalError = globalError;
	}

	public List<FieldErrorDto> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorDto> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
