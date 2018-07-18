package employees;

public class Pair {
	
	// first partner ID
	private int firstPartner;
	// second partner ID
	private int secondPartner;
	// time spent working together
	private int timeTogether;

	public Pair(int idEmp1, int idEmp2, int time) {
		this.setFirstPartner(idEmp1);
		this.setSecondPartner(idEmp2);
		this.setTimeTogether(time);
	}
		
	public void increaseTime(int time) {
		if(Company.checkIfIsNotPositive(time)) {
			throw new IllegalArgumentException("Cannot increase time spent from a team with non-positive value! '" + time + "'");
		}
			
		this.timeTogether+= time;
	}
		
	private void setFirstPartner(int id) {
		if(Company.checkIfIsNotPositive(id)) {
			throw new IllegalArgumentException("Partner must have positive value as an ID! '" + id + "'");
		}

		this.firstPartner = id;
	}
		
	private void setSecondPartner(int id) {
		if(Company.checkIfIsNotPositive(id)) {
			throw new IllegalArgumentException("Partner must have positive value as an ID! '" + id + "'");
		}

		this.secondPartner = id;
	}

	private void setTimeTogether(int time) {
		if(Company.checkIfIsNotPositive(time)) {
			throw new IllegalArgumentException("Time spent by pair cannot be non-positive value! '" + time + "'");
		}
			
		this.timeTogether = time;
	}
		
	public long getTimeTogether() {
		return timeTogether;
	}
		
	// Override hashashCode() to return exact same value for the same team
	// Example: hashCode() of team (11;17) and team (17;11) is one unique value	
	@Override	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
			
		if(firstPartner < secondPartner) {
			result = prime * result + firstPartner;   
			result = prime * result + secondPartner;
		}
		else {
			result = prime * result + secondPartner;   
			result = prime * result + firstPartner;
		}
			
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// if there is already pair for that team
		Pair temp = (Pair) obj;
			
		// increase working hours spent together
		temp.increaseTime(this.timeTogether);
			
		// do not add the "new" Pair
		return true;
	}

	@Override
	public String toString() {
		return "IDs => [" + firstPartner + ", " + secondPartner + "]";
	}
}
