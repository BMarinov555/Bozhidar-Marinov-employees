package exceptions;

public class WrongOrderOfDatesException extends Exception {
	@Override
	public String getMessage() {
		return "Wrong order of dates!";
	}
}
