package com.capgemini.employeepayrollservice;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmployeePayrollServiceThreadTest {

	private EmployeePayrollService employeePayrollService;
	private List<EmployeePayroll> employeeList;

	@Before
	public void init() {
		employeePayrollService = new EmployeePayrollService();
	}

	@Test
	public void givenListOfEmployeesWhenAddedShouldMatchNoOfEntries() throws DatabaseException {
		List<EmployeePayroll> employeeList = new ArrayList<>();
		employeeList.add(new EmployeePayroll("Radha", 'F', 300000.00, LocalDate.now()));
		employeeList.add(new EmployeePayroll("Mahesh", 'M', 200000.00, LocalDate.now()));
		employeeList.add(new EmployeePayroll("Teena", 'F', 400000.00, LocalDate.now()));
		employeeList.add(new EmployeePayroll("Harry", 'M', 250000.00, LocalDate.now()));
		employeeList.add(new EmployeePayroll("Chandu", 'M', 450000.00, LocalDate.now()));
		Instant start = Instant.now();
		employeePayrollService.addEmployeeListToTable(employeeList);
		Instant end = Instant.now();
		Instant startThread = Instant.now();
		employeePayrollService.addEmployeeListToTableWithThreads(employeeList);
		Instant endThread = Instant.now();
		employeeList = employeePayrollService.readData();
		System.out.println("Duration without Threads : " + Duration.between(start, end));
		System.out.println("Duration without Threads : " + Duration.between(startThread, endThread));
		Assert.assertEquals(10, employeeList.size());
	}

	@Test
	public void givenListOfUpdatedSalaryWhenUpdatedShouldSyncWithDatabase() throws DatabaseException {
		Map<String, Double> nameToUpdatedSalary = new HashMap<>();
		nameToUpdatedSalary.put("Radha", 3500000.00);
		nameToUpdatedSalary.put("Chandu", 3000000.00);
		boolean result = false;
		Instant startThread = Instant.now();
		employeePayrollService.updateSalaryList(nameToUpdatedSalary);
		Instant endThread = Instant.now();
		
		result = employeePayrollService.checkEmployeeDataInSyncWithDatabase("Radha");
		System.out.println("Duration with Threads : " + Duration.between(startThread, endThread));
		Assert.assertTrue(result);
	}

}
