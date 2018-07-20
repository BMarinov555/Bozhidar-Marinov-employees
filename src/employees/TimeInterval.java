package employees;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		this.from = this.convertToDate(from);
	}
		
	private void setTo(String to) throws ParseException, WrongOrderOfDatesException, IncorrectDateFormatException {
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
	
	private Date convertToDate(String date) throws ParseException, IncorrectDateFormatException {
		
		// invalid date
		if(date == null || date.equals("")) {
			throw new IncorrectDateFormatException();
		}
		
		// valid date - return today
		if(date.equals("NULL")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// return current date
			return sdf.parse( sdf.format(new Date()) );
		}
		
		// date delimiter 
		char delimiter = this.getDateDelimiter(date);
		
		// invalid date
		if(delimiter == 0) {
			throw new IncorrectDateFormatException();
		}
		
		SimpleDateFormat sdf = null;
		
		// check if date format is YMD
		if(isYearFirst(date)) {
			sdf = new SimpleDateFormat("yyyy" + delimiter + "MM" + delimiter + "dd");
		}
		else {
			// check if date format is DMY
			if(isYearLast(date)) {
				sdf = new SimpleDateFormat("dd" + delimiter + "MM" + delimiter + "yyyy");
			}
			else {
				// invalid format
				throw new IncorrectDateFormatException();
			}
		}
		
		return sdf.parse(date);
	}
	
	// returns 0 if find more than one type of delimiter - incorrect date
	private char getDateDelimiter(String str) {

		// regular expression that matches every char but digit
		Pattern p = Pattern.compile("[^0-9]");
		Matcher m = p.matcher(str);
		    
		char delimiter = 0;
		 
		// loop through every delimiter
		//if there are at least 2 different -> incorrect date
		while(m.find()) {
		    String match = m.group(0);
		    char tempChar = match.charAt(0);
		    	
		    if(delimiter != 0 && delimiter != tempChar) {
		    	return 0;
		    }
		    	
		    delimiter = match.charAt(0);
		}
		     
		return delimiter;
	}
	
	private boolean isYearFirst(String date) {
		// check if date starts with 4 digits
		Pattern p = Pattern.compile("^[0-9]{4}");
	    Matcher m = p.matcher(date);
	    
		return m.find();
	}
	
	private boolean isYearLast(String date) {
		// check if date ends with 4 digits
		Pattern p = Pattern.compile("[0-9]{4}$");
	    Matcher m = p.matcher(date);
	    
		return m.find();
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
	
	//in use only if dates are in right order - no need to check
	private static int daysBetween(Date date1, Date date2) {
			
		// 															add one day 
		long timeInMillis = date2.getTime() - date1.getTime() + TimeUnit.DAYS.toMillis(1);
		    
		// convert milliseconds to days
		return (int) TimeUnit.DAYS.convert(timeInMillis, TimeUnit.MILLISECONDS);
	}
}
