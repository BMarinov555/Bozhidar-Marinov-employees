package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import employees.Employee;

public class Reader {

	enum Index {
		IDEMP(0), IDPROJECT(1), FIRSTDAY(2), LASTDAY(3);
		
		int id;
		
		Index(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
	
	private Scanner sc;
	
	public Reader(File file) throws Exception {
		
		if(file == null) {
			throw new NullPointerException("Null given as a file parameter in Reader constructor!");
		}
		
		try {
			this.sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new Exception("File does not exists: '" + file.getPath() + "'.");
			//System.out.println(e.getMessage());
		}
	}
	
	public Map<Integer, Employee> convertFileInformation() {
		
		//Integer represents ID of an Employee
		Map<Integer, Employee> employees = new HashMap<>();
				
		// iterate over every line of file
		while(sc.hasNextLine()) {
					
			// split line by commas
			String[] elements = sc.nextLine().split(",");
					
			// if line is not appropriate -> skip it
			if(elements.length != 4) {
				continue;
			}
			
			int empId = Integer.parseInt(elements[Index.IDEMP.getId()].trim());
			int projectId = Integer.parseInt(elements[Index.IDPROJECT.getId()].trim());
			String from = elements[Index.FIRSTDAY.getId()].trim();
			String to = elements[Index.LASTDAY.getId()].trim();
			
			// if there is already an Entry for that employee
			if(employees.get(empId) != null) {
				
				// add one more project
				try {
					employees.get(empId).addProject(projectId, from, to);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					System.out.println("Skipping following line: '" + empId + ", " + projectId + ", " + from + ", " + to);
				} 
				catch (ParseException e) {
					System.out.println("Cannot parse date!");
					System.out.println("Skipping following line: '" + empId + ", " + projectId + ", " + from + ", " + to);
				}
			}
			else {
				// else add new Entry
				try {
					employees.put(empId, new Employee(empId, projectId, from, to));
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					System.out.println("Skipping following line: '" + empId + ", " + projectId + ", " + from + ", " + to);
				} 
				catch (ParseException e) {
					System.out.println("Cannot parse date!");
					System.out.println("Skipping following line: '" + empId + ", " + projectId + ", " + from + ", " + to);
				}
			}
		}
		
		return employees;
	}
	
	public static File getUserInputFile() {
		
		System.out.println("Enter path to the file:");
		
		Scanner reader = new Scanner(System.in);
		File file = null;
		String line;
		
		while(true) {
			
			// read from input
			line = reader.nextLine();
			
			if(line.equals("exit")) {
				System.out.println("Terminated successfully!");
				file = null;
				break;
			}
			
			file = new File(line);
			
			// if there is no such file -> ask again
			if(!file.exists()) {
				System.out.println("There is no such file! Try again or type");
				System.out.println("exit");
				continue;
			}
			
			break;
		}
		
		reader.close();

		return file;
	}
	
	public void close() {
		this.sc.close();
	}
}
