package es.udc.tfg.tfgprojectbackend.model.exceptions;

/**
 * Exception thrown when an instance is not found in the database.
 */
@SuppressWarnings("serial")
public class InstanceNotFoundException extends InstanceException {
    
    public InstanceNotFoundException(String name, Object key) {
    	super(name, key); 	
    }

}
