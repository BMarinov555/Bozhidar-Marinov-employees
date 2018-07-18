package employees;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


//Utility class
public final class Company {
	
	private Company() {};
	
	public static void printBestPair(Set<Pair> allPairs) {
		
		if(allPairs.isEmpty()) {
			System.out.println("There are no pairs at all.");
			return;
		}
		
		// Let's find the best pair - the pair that has most working days together
		// bestPair set as "first" pair of the set
		Pair bestPair = allPairs.iterator().next();
		
		// iterator to go through every other pair in the set
		Iterator<Pair> iterator = allPairs.iterator();
		
		// set iterator to points to "second" element of the set
		iterator.next();
		
		// loop through every pair left
		while(iterator.hasNext()) {
			// current pair
			Pair p = iterator.next();
			
			// if current has more time than previous best
			if(bestPair.getTimeTogether() < p.getTimeTogether()){
				// set current as new best
				bestPair = p;
			}
		}
		
		System.out.println("Pair with most days spent together: ");
		System.out.println(bestPair);
		System.out.println("Total days spent on projects: " + bestPair.getTimeTogether());
	}
	
	public static Set<Pair> getAllPairs(Map<Integer, Employee> employees){
		
		if(employees == null) {
			return null;
		}
		
		// Set - containing all pairs
		Set<Pair> allPairs = new HashSet<>();
				
		// iterate over every two employees to check if they have been working together on some project(s)
		for(Employee emp1 : employees.values()) {
			
			// using flag to skip iterations
			boolean flag = false;
					
			// skips iterations until it's current employee passes the current employee of first for loop
			for(Employee emp2 : employees.values()) {
						
				if(flag == false) {
							
					// if just passed the current element of first for
					if(emp1 == emp2) {
						// next step is not going to be "pass"
						flag = true;
					}
							
					// skip this step 
					continue;
				}
				
				for(Map.Entry<Integer, TimeInterval> firstEmpProject : emp1.getProjects().entrySet()) {
					
					// look for same project in secondEmployee's projects
					TimeInterval secondEmpProject = emp2.getProjects().get(firstEmpProject.getKey());
							
					// if second employee does not have that project -> keep searching for others
					if(secondEmpProject == null) {
						continue;
					}
							
					// get total time spent working together on this current project
					int timeSpentTogether = TimeInterval.calculateParallelDays(firstEmpProject.getValue(), secondEmpProject);
					
					if(timeSpentTogether > 0) {
						// add team to allPairs OR add time interval to already existing team in allPairs
						try {
							allPairs.add(new Pair(emp1.getId(), emp2.getId(), timeSpentTogether));
						}catch (IllegalArgumentException e) {
							System.out.println(e.getMessage());
							System.out.println("Skipping pair [" + emp1.getId() + ", " + emp2.getId() +"]");
						}
					}
						
				}
			}
		}
		
		return allPairs;
	}
	
	public static boolean checkIfIsNotPositive(int number) {
		if(number < 1) {
			return true;
		}
		
		return false;
	}
}
