package employees;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import exceptions.IncorrectDateFormatException;
import exceptions.WrongOrderOfDatesException;
import employees.Project;
import employees.Company;
import employees.TimeInterval;

public class Employee {

	//Employee ID
	private int id;
		
	// Integer represents Project ID
	private Map<Integer, Project> projects;
		
	public Employee(int id, int projectId, String from, String to) throws IllegalArgumentException, ParseException {
		this.setId(id);
		this.projects = new HashMap<Integer, Project>();
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

	public Map<Integer, Project> getProjects() {
		return Collections.unmodifiableMap(this.projects);
	}
	
	public void addProject(int projectId, String from, String to) throws ParseException,IllegalArgumentException {
		
		// projectId must be positive
		if(Company.checkIfIsNotPositive(projectId)) {
			throw new IllegalArgumentException("Cannot add project with non-positive ID! '" + projectId + "'");
		}
	
		TimeInterval interval;
		try {
			interval = new TimeInterval(from, to);
		} catch (WrongOrderOfDatesException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (IncorrectDateFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
		// if there is already Entry in Map for that project - add new interval (if it is correct)
		if(this.projects.get(projectId) != null) {
			try {
				this.projects.get(projectId).addNewInterval(interval);
			} catch (NullPointerException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
			
			return;
		}
		
		// else add Entry for that project
		this.projects.put(projectId, new Project(projectId, interval));
	}
}
