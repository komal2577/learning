package com.junitguide.springboottesting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junitguide.springboottesting.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	  Optional<Employee> findByEmail(String email);

}
