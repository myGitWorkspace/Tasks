package src.java.model;

public class NotEnoughProgrammersForProjectException extends Exception {
	
	public NotEnoughProgrammersForProjectException() {
		
	}
	
	public NotEnoughProgrammersForProjectException(String message, Throwable exception) {
		super(message, exception);
	}
	
	public NotEnoughProgrammersForProjectException(String message) {
		super(message);
	}
	
	public NotEnoughProgrammersForProjectException(Throwable exception) {
		super(exception);
	}
}
