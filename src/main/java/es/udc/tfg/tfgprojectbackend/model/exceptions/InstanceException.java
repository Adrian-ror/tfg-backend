package es.udc.tfg.tfgprojectbackend.model.exceptions;


/**
 * This class is the superclass of all exceptions that can be thrown when an instance of a class is not found in the database.
 * It has two attributes: name and key. The name attribute is the name of the class that has thrown the exception and the key attribute is the key of the instance that has not been found.
 */

//@SuppressWarnings annotation is used to suppress warnings issued by the compiler.
//@SuppressWarnings("serial") annotation tells the compiler that it should not issue warnings
// when classes are missing serialVersionUID field.
@SuppressWarnings("serial")
public abstract class InstanceException extends Exception {
    
    private String name;
    private Object key;


    protected InstanceException(String message) {
    	super(message);
    }
    
    public InstanceException(String name, Object key) {
    	
		this.name = name;
		this.key = key;
		
	}


	public String getName() {
		return name;
	}

	public Object getKey() {
		return key;
	}

}
