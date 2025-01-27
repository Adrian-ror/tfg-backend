package es.udc.tfg.tfgprojectbackend.model.exceptions;


/**
 * Exception thrown when trying to create an instance that already exists in the database.
 */

@SuppressWarnings("serial")
public class DuplicateInstanceException extends InstanceException {

    public DuplicateInstanceException(String name, Object key) {
    	super(name, key); 	
    }
    
}
