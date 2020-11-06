package com.capgemini.employeepayrollservice;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EmployeePayrollServiceRestAssureTest {

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}
	
	private EmployeePayroll[] getEmployeeList() {
		Response response = RestAssured.get("/employees");
		System.out.println("Employee payroll entries in JSON Server :\n" + response.asString());
		EmployeePayroll[] arrayOfEmployees = new Gson().fromJson(response.asString(), EmployeePayroll[].class);
		return arrayOfEmployees;
	}
	
	@Test
	public void givenNewEmployeeWhenAddedShouldMatch() {
		EmployeePayrollService employeePayrollService;
		EmployeePayroll[] arrayOfEmployees = getEmployeeList();
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmployees));
		long entries = employeePayrollService.countEntries();
		Assert.assertEquals(5, entries);
	}

}
