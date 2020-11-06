package com.capgemini.employeepayrollservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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

	public Response addEmployeeToJsonServer(EmployeePayroll employeePayroll) {
		String empJson = new Gson().toJson(employeePayroll);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		return request.post("/employees");
	}

	@Test
	public void givenNewEmployeeWhenRetrievedShouldMatch() {
		EmployeePayrollService employeePayrollService;
		EmployeePayroll[] arrayOfEmployees = getEmployeeList();
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmployees));
		long entries = employeePayrollService.countEntries();
		Assert.assertEquals(5, entries);
	}

	@Test
	public void givenListOfEmployeeWhenAddedShouldMatchResponseCode() {
		EmployeePayrollService employeePayrollService;
		EmployeePayroll[] arrayOfEmployees = getEmployeeList();
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmployees));
//		List<EmployeePayroll> employeePayrollList = new ArrayList<>();
		EmployeePayroll employeePayroll = new EmployeePayroll(0, "Malavika", 4000000.00, LocalDate.now());
		System.out.println(employeePayroll);
		Response response = addEmployeeToJsonServer(employeePayroll);
		System.out.println(response);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);

		employeePayroll = new Gson().fromJson(response.asString(), EmployeePayroll.class);
		employeePayrollService.addEmployeePayroll(employeePayroll);
		long entries = employeePayrollService.countEntries();
		Assert.assertEquals(9, entries);
	}

}
