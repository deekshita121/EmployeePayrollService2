package com.capgemini.employeepayrollservice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.capgemini.employeepayrollservice.DatabaseException.exceptionType;
import com.capgemini.employeepayrollservice.EmployeePayrollDBService.statementType;

public class EmployeePayrollService {

	private static EmployeePayrollService employeePayrollService;
	private static EmployeePayrollDBService employeePayrollDBService;
	List<EmployeePayroll> employeePayrollList;
	PreparedStatement preparedStatement;

	public EmployeePayrollService(List<EmployeePayroll> employeePayrollList) {
		this.employeePayrollList = new ArrayList<>(employeePayrollList);
	}
	
	public EmployeePayrollService() {
		employeePayrollDBService = employeePayrollDBService.getInstance();
	}

	// To read payroll Data from database
	public List<EmployeePayroll> readData() throws DatabaseException {
		employeePayrollList = employeePayrollDBService.readDataDB();
		return employeePayrollList;
	}

	// To update data in the database
	public void updateData(String name, double salary, statementType type) throws DatabaseException {
		employeePayrollList = employeePayrollDBService.readDataDB();
		int rowAffected = employeePayrollDBService.updateDataDB(name, salary, type);
		if (rowAffected != 0)
			(getEmployeeByName(employeePayrollList, name)).setSalary(salary);
	}

	private EmployeePayroll getEmployeeByName(List<EmployeePayroll> employeePayrollList, String name) {
		EmployeePayroll employee = employeePayrollList.stream()
				.filter(employeeObj -> ((employeeObj.getName()).equals(name))).findFirst().orElse(null);
		return employee;
	}

	// To check the database after updating
	public boolean checkEmployeeDataInSyncWithDatabase(String name) throws DatabaseException {
		boolean result = false;
		employeePayrollList = employeePayrollDBService.readDataDB();
		EmployeePayroll employee = employeePayrollDBService.getEmployeeDataByNameDB(name).get(0);
		result = (getEmployeeByName(employeePayrollList, name)).equals(employee);
		return result;
	}

	// To get employee data joined after a particular date
	public List<EmployeePayroll> getEmployeeDataByDate(LocalDate start, LocalDate endDate) throws DatabaseException {
		return employeePayrollDBService.getEmployeeDataByDateDB(start, endDate);
	}

	// To get sum of salaries of male and female employees
	public Map<String, Double> getDataGroupedByGender(String operation, String column) throws DatabaseException {
		return employeePayrollDBService.getDataGroupedByGenderDB(operation, column);
	}

	// To add new employee to database
	public void addEmployeeData(String name, char gender, double salary, LocalDate start) throws DatabaseException {
		EmployeePayroll employee = employeePayrollDBService.addEmployeeDataDB(name, gender, salary, start);
		if (employee.getId() != -1)
			employeePayrollList.add(employee);
	}

	// To add employee details to both tables
	public void addEmployeeToEmployeeAndPayroll(String name, char gender, double salary, LocalDate start)
			throws DatabaseException {
		EmployeePayroll employee = employeePayrollDBService.addEmployeeToEmployeeAndPayrollDB(name, gender, salary,
				start);
		if (employee.getId() != -1)
			employeePayrollList.add(employee);
	}

	public void addEmployeeToAllRelatedTables(EmployeePayroll employeePayroll) throws DatabaseException {
		EmployeePayroll employee = employeePayrollDBService.addEmployeeToAllRelatedTablesDB(employeePayroll);
		if (employee.getId() != -1)
			employeePayrollList.add(employee);
	}

	public void removeEmployeeFromPayrollTable(String string) throws DatabaseException {
		employeePayrollList = employeePayrollDBService.readDataDB();
		String name = null;
		int rowAffected = employeePayrollDBService.removeEmployeeFromPayrollTableDB(name);
		if (rowAffected != 0) {
			EmployeePayroll employee = getEmployeeByName(employeePayrollList, name);
			employeePayrollList.remove(employee);
		}
	}

	public boolean checkActiveStatus(String string) throws DatabaseException {
		String name = null;
		return employeePayrollDBService.checkActiveStatusDB(name);
	}

	public void addEmployeeListToTable(List<EmployeePayroll> employeeList) throws DatabaseException {
		for (EmployeePayroll employee : employeeList) {
			addEmployeeData(employee.getName(), employee.getGender(), employee.getSalary(), employee.getStart());
		}

	}

	public void addEmployeeListToTableWithThreads(List<EmployeePayroll> employeeList) {
		Map<Integer, Boolean> employeeAditionStatus = new HashMap<>();
		employeeList.forEach(employee -> {
			Runnable task = () -> {
				employeeAditionStatus.put(employee.hashCode(), false);
				System.out.println("Employee being added : " + employee.getName());
				try {
					addEmployeeData(employee.getName(), employee.getGender(), employee.getSalary(),
							employee.getStart());
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
				employeeAditionStatus.put(employee.hashCode(), true);
				System.out.println("Employee added : " + employee.getName());
			};
			Thread thread = new Thread(task, employee.getName());
			thread.start();
		});

		while (employeeAditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateSalaryList(Map<String, Double> nameToUpdatedSalary) {
		Map<Integer, Boolean> salaryUpdateStatus = new HashMap<>();
		nameToUpdatedSalary.forEach((employeeName, salary) -> {
			Runnable task = () -> {
				salaryUpdateStatus.put(employeeName.hashCode(), false);
				try {
					updateData(employeeName, salary, statementType.STATEMENT);
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
				salaryUpdateStatus.put(employeeName.hashCode(), true);
			};
			Thread thread = new Thread(task, employeeName);
			thread.start();
		});

		while (salaryUpdateStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void addEmployeePayroll(EmployeePayroll employeePayroll) {
		employeePayrollList.add(employeePayroll);

	}
	
	public long countEntries() {
		return employeePayrollList.size();
	}

	public void updateEmployeeList(String name, double salary) {

		(getEmployeeByName(employeePayrollList, name)).setSalary(salary);
	}

	public EmployeePayroll getEmployee(String name)
	{
		EmployeePayroll employee = employeePayrollList.stream()
				.filter(employeeObj -> ((employeeObj.getName()).equals(name))).findFirst().orElse(null);
		return employee;

	}

	public void removeEmployee(String name) {
		EmployeePayroll employee = getEmployeeByName(employeePayrollList, name);
		employeePayrollList.remove(employee);
	}

}