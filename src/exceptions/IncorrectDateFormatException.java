package exceptions;

public class IncorrectDateFormatException extends Exception {
	@Override
	public String getMessage() {
		return "Invalid date format!";
	}
}
