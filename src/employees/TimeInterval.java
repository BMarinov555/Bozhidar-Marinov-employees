package employees;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import exceptions.IncorrectDateFormatException;
import exceptions.WrongOrderOfDatesException;

public class TimeInterval {
	
	// first day on project
	private Date from;
	// last day on project
	private Date to;
		
	public TimeInterval(String from, String to) throws WrongOrderOfDatesException, IncorrectDateFormatException, ParseException {
		this.setFrom(from);
		this.setTo(to);
	}
		
	private void setFrom(String from) throws  IncorrectDateFormatException, ParseException {
		if(!this.checkDate(from)) {
			throw new IncorrectDateFormatException();
		}
			
		this.from = this.convertToDate(from);
	}
		
	private void setTo(String to) throws ParseException, WrongOrderOfDatesException, IncorrectDateFormatException {
		if(!this.checkDate(to)) {
			throw new IncorrectDateFormatException();
		}
			
		Date date = this.convertToDate(to);
			
		// check if dates are in right order
		if(this.from.compareTo(date) < 0) {
			this.to = date;
		}
		else {
			throw new WrongOrderOfDatesException();
		}
	}
		
	public Date getFrom() {
		return from;
	}
		
	public Date getTo() {
		return to;
	}
	
	private boolean checkDate(String date) {
		if(date == null || date.equals("")) {
			return false;
		}
		
		// "NULL" is acceptable date
		if(date.equals("NULL")) {
			return true;
		}
		
		// else check if date format is like: 2000-11-01
		if(!date.matches("[12][0-9]{3}-[01][0-9]-[0-3][0-9]")) {
			return false;
		}
		
		return true;
	}
	
	private Date convertToDate(String str) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(str.equals("NULL")) {
			// return current date
			return sdf.parse( sdf.format(new Date()) );
		}
		
		return sdf.parse(str);
	}
	
	public static int calculateParallelDays(TimeInterval firstInterval, TimeInterval secondInterval) {
		
		// first and last day of first interval
		Date firstStart = firstInterval.from;
		Date firstFinish = firstInterval.to;
		
		// first and last day of second interval
		Date secondStart = secondInterval.from;
		Date secondFinish = secondInterval.to;
		
		// if first interval finishes before second starts OR second finishes before first starts
		if( (firstFinish.compareTo(secondStart) < 0) || (secondFinish.compareTo(firstStart) < 0) ) {
			return 0;
		}
		
		// if both of the intervals starts at the same day
		if(firstStart.compareTo(secondStart) == 0) {
			
			// if first interval finishes after second does
			if(firstFinish.compareTo(secondFinish) > 0) {
				// return second
				return (int) TimeInterval.daysBetween(secondStart, secondFinish);
			}
			// else return first
			return (int) TimeInterval.daysBetween(firstStart, firstFinish);
		}
		
		// if both of the intervals finishes at the same time
		if(firstFinish.compareTo(secondFinish) == 0) {
			
			// if first interval starts before second does
			if(firstStart.compareTo(secondStart) < 0) {
				// return second
				return (int) TimeInterval.daysBetween(secondStart, secondFinish);
			}
			// else return first
			return (int) TimeInterval.daysBetween(firstStart, firstFinish);
		}
		
		// if first interval starts before second interval starts
		if(firstStart.compareTo(secondStart) < 0) {
			
			// if second interval finishes before first finishes
			if(secondFinish.compareTo(firstFinish) < 0) {
				// return days between start of second and its finish
				return (int) TimeInterval.daysBetween(secondStart, secondFinish);
			}
			// else return days between start of second and finish of first
			return (int) TimeInterval.daysBetween(secondStart, firstFinish);
		}
		
		// else second interval starts before first interval starts
			
		// if first interval finishes before second does
		if(firstFinish.compareTo(secondFinish) < 0) {
			// return days between start of first and its finish
			return (int) TimeInterval.daysBetween(firstStart, firstFinish);
		}
		
		// else return days between start of first and finish of second
		return (int) TimeInterval.daysBetween(firstStart, secondFinish);
	}
	
	private static long daysBetween(Date date1, Date date2) {
		
		// this method is in use only if dates are in right order - no need to check
		long timeInMillis = date2.getTime() - date1.getTime();
	    
		// convert milliseconds to days
		return TimeUnit.DAYS.convert(timeInMillis, TimeUnit.MILLISECONDS);
	}
	
}
