package com.junitguide.springboottesting.service;

import java.util.List;
import java.util.Optional;

import com.junitguide.springboottesting.model.Employee;

public interface EmployeeService {
	Employee saveEmployee(Employee employee);
	List<Employee> getAllEmployees()  throws Exception;
	Optional<Employee> getEmployeeById(long id);
	Employee updateEmployee(Employee updatedEmployee);
	  void deleteEmployee(long id);
}
