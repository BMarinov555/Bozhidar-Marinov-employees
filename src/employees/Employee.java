package employees;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import exceptions.IncorrectDateFormatException;
import exceptions.WrongOrderOfDatesException;
import employees.Company;
import employees.TimeInterval;

public class Employee {

	//Employee ID
	private int id;
		
	// Integer represents Project ID
	private Map<Integer, TimeInterval> projects;
		
	public Employee(int id, int projectId, String from, String to) throws IllegalArgumentException, ParseException {
		this.setId(id);
		this.projects = new HashMap<Integer, TimeInterval>();
		this.addProject(projectId, from, to);
	}
		
	private void setId(int id) {
		if(Company.checkIfIsNotPositive(id)) {
			throw new IllegalArgumentException("ID of an Employee must be positive! '" + id + "'");
		}
			
		this.id = id;
	}
		
	public int getId() {
		return id;
	}
		
	public TimeInterval getProjectTimeInterval(Integer projectId) {
		return this.projects.get(projectId);
	}

	public Map<Integer, TimeInterval> getProjects() {
		return Collections.unmodifiableMap(this.projects);
	}
	
	public void addProject(int projectId, String from, String to) throws ParseException {
		
		// projectId must be positive
		if(Company.checkIfIsNotPositive(projectId)) {
			throw new IllegalArgumentException("Cannot add project with non-positive ID! '" + projectId + "'");
		}
		
		// if there is already Entry in Map for that project - do not add
		if(this.projects.get(projectId) != null) {
			return;
		}
		
		try {
			this.projects.put(projectId, new TimeInterval(from, to));
		} catch (WrongOrderOfDatesException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (IncorrectDateFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
}
