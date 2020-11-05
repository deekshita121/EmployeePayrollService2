package com.capgemini.employeepayrollservice;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
		employeeList = employeePayrollService.readData();
        System.out.println("Duration without Threads : " + Duration.between(start, end));
		Assert.assertEquals(5, employeeList.size());
	}

}
