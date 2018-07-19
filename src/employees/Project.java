package employees;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Project {

	// Project ID
	private int id;
	// all time intervals for this project
	private Set<TimeInterval> intervals;
		
	public Project(int id, TimeInterval interval) throws IllegalArgumentException {
		if(Company.checkIfIsNotPositive(id)) {
			throw new IllegalArgumentException("Project ID must be positive!");
		}
		this.id = id;
			
		this.intervals = new TreeSet<>( new Comparator<TimeInterval>() {

			@Override
			public int compare(TimeInterval o1, TimeInterval o2) {
					
				int parallelDays = TimeInterval.calculateParallelDays(o1, o2);
					
				// if these two intervals have days together -> the new one is incorrect so do NOT add it
				if(parallelDays != 0) {
					return 0;
				}
					
				// if first interval is before second
				if(o1.getFrom().compareTo(o2.getFrom()) < 0) {
					return -1;
				}
					
				// else
				return 1;
			}
		} );
		
		try {
			this.addNewInterval(interval);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
		
	public int getId() {
		return id;
	}
		
	public Set<TimeInterval> getIntervals() {
		return Collections.unmodifiableSet(this.intervals);
	}
		
	// add time interval - complexity O( log(n) )
	void addNewInterval(TimeInterval interval) throws NullPointerException {
		if(interval == null) {
			throw new NullPointerException("Cannot add 'null' interval!");
		}
		
		this.intervals.add(interval);
	}
}
