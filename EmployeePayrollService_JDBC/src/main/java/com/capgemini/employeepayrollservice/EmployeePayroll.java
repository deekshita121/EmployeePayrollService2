package com.capgemini.employeepayrollservice;

import java.time.LocalDate;
import java.util.Arrays;

public class EmployeePayroll {

	private int employeeId;
	private String name;
	private int companyId;
	private String phoneNumber;
	private String address;
	private char gender;
	private Double salary;
	private LocalDate startDate;
	private int[] departmentId;

	// Constructor
	public EmployeePayroll(int id, String name, char gender, Double salary, LocalDate startDate) {
		this(name, gender, salary, startDate);
		this.employeeId = id;
	}

	public EmployeePayroll(String name, char gender, Double salary, LocalDate startDate) {
		this.name = name;
		this.gender = gender;
		this.salary = salary;
		this.startDate = startDate;
	}

	public EmployeePayroll(int id, String name, Double salary, LocalDate startDate) {
		this.employeeId = id;
		this.name = name;
		this.salary = salary;
		this.startDate = startDate;
	}

	// Getters and Setters
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int[] getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int[] departmentId) {
		this.departmentId = departmentId;
	}

	public int getId() {
		return employeeId;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public void setId(int id) {
		this.employeeId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "EmployeePayroll [employeeId=" + employeeId + ", name=" + name + ", companyId=" + companyId
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + ", gender=" + gender + ", salary=" + salary
				+ ", startDate=" + startDate + ", departmentId=" + Arrays.toString(departmentId) + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeePayroll other = (EmployeePayroll) obj;
		if (employeeId != other.employeeId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

}
