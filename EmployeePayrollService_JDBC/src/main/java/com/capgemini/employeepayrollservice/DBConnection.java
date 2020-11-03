package com.capgemini.employeepayrollservice;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import com.capgemini.employeepayrollservice.DatabaseException.exceptionType;




public class DBConnection 
{
	
	 static String url = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
	 static String userName = "root";
	 static String password = "Dhruv@876A";
	 static java.sql.Connection con = null;
	 
	 
	 public static java.sql.Connection getConnection () throws DatabaseException
		{
			try {
				//Driver Loading
				Class.forName("com.mysql.cj.jdbc.Driver"); 
				//Making the connection to Database
				con = DriverManager.getConnection(url,userName,password); 
				System.out.println("Connection Successful");
				
			} catch (Exception e) {
				throw new DatabaseException("Unable to load the driver!!", exceptionType.DRIVER_CONNECTION);
				
			}
			
			return con;
		}	 
}
