package exceptions;

public class WrongOrderOfDatesException extends Exception {
	@Override
	public String toString() {
		return "Wrong order of dates!";
	}
}
