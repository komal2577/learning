package com.junitguide.springboottesting.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junitguide.springboottesting.exception.ResourceNotFoundException;
import com.junitguide.springboottesting.model.Employee;
import com.junitguide.springboottesting.repository.EmployeeRepository;
import com.junitguide.springboottesting.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if(savedEmployee.isPresent()){
            throw new ResourceNotFoundException("Employee already exist with given email:" + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

	@Override
	public List<Employee> getAllEmployees() throws Exception {
		List<Employee> employeeList = new ArrayList();
		try {
			employeeList = employeeRepository.findAll();
		}catch(Exception e) {
			throw new ResourceNotFoundException("error while fectching the records from database");
		}
		return employeeList;
	}

	/*
	 * @Override public Optional<Employee> getEmployeeById(long id) { // TODO
	 * Auto-generated method stub Optional<Employee> employee =
	 * employeeRepository.findById(id); //System.out.println(employee.toString());
	 * if(employee.isEmpty()){ throw new
	 * ResourceNotFoundException("Employee not found with id :" + id); } return
	 * employee; }
	 */
	
    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }
	
    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.save(updatedEmployee);
    }
    
    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
		
       
 }

	
	


