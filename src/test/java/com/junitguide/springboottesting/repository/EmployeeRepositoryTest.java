package com.junitguide.springboottesting.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.junitguide.springboottesting.model.Employee;

@DataJpaTest
public class EmployeeRepositoryTest {
	
	 @Autowired
	 private EmployeeRepository employeeRepository;
	 
	 @Test
	 public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

	        Employee employee = Employee.builder()
	                .firstName("Komal")
	                .lastName("Pawar")
	                .email("kopawar@gmail.com")
	                .build();
	       
	        Employee savedEmployee = employeeRepository.save(employee);

	        assertThat(savedEmployee).isNotNull();
	        assertThat(savedEmployee.getId()).isPositive();
	        assertEquals("Komal", savedEmployee.getFirstName());       
	    }
	 
	 
	     @Test
	    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
	       
	    	 Employee employee = Employee.builder()
		                .firstName("Komal")
		                .lastName("Pawar")
		                .email("kopawar@gmail,com")
		                .build();

	        Employee employee1 = Employee.builder()
	                .firstName("Titiksha")
	                .lastName("pawar")
	                .email("titikshapawar@gmail,com")
	                .build();

	        employeeRepository.save(employee);
	        employeeRepository.save(employee1);
	        List<Employee> employeeList = employeeRepository.findAll();
	        assertThat(employeeList).isNotNull();
	        assertThat(employeeList.size()).isEqualTo(2);

	    }
	     
	     @Test
	     public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
	       
	         Employee employee = Employee.builder()
	                 .firstName("Ramesh")
	                 .lastName("Fadatare")
	                 .email("ramesh@gmail,com")
	                 .build();
	         employeeRepository.save(employee);
	         Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();
	         assertThat(employeeDB).isNotNull();
	     }
	     
	     @Test
	     public void givenEmployeeEmail_whenFindById_thenReturnEmployeeObject(){
	       
	         Employee employee = Employee.builder()
	                 .firstName("Ramesh")
	                 .lastName("Fadatare")
	                 .email("ramesh@gmail,com")
	                 .build();
	         employeeRepository.save(employee);
	         Employee employeeDB = employeeRepository.findById(employee.getId()).get();
	         assertThat(employeeDB).isNotNull();
	     }

}
