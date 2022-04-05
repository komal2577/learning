package com.junitguide.springboottesting.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.junitguide.springboottesting.exception.ResourceNotFoundException;
import com.junitguide.springboottesting.model.Employee;
import com.junitguide.springboottesting.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);

	}

	@GetMapping
	public List<Employee> getAllEmployees() throws Exception {
		return employeeService.getAllEmployees();
	}

	
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId){
        return employeeService.getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
	 
	 
		/*
		 * @PutMapping("{id}") public ResponseEntity<Employee>
		 * updateEmployee(@PathVariable("id") long employeeId,
		 * 
		 * @RequestBody Employee employee){ Employee updatedEmployee =
		 * employeeService.updateEmployee(employeeId,employee); return new
		 * ResponseEntity<>(updatedEmployee, HttpStatus.OK); }
		 */
	 
	 
	    @PutMapping("{id}")
	    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeId,
	                                                   @RequestBody Employee employee){
	        return employeeService.getEmployeeById(employeeId)
	                .map(savedEmployee -> {

	                    savedEmployee.setFirstName(employee.getFirstName());
	                    savedEmployee.setLastName(employee.getLastName());
	                    savedEmployee.setEmail(employee.getEmail());

	                    Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);
	                    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);

	                })
	                .orElseGet(() -> ResponseEntity.notFound().build());
	    }
	    
	    @DeleteMapping("{id}")
	    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId){
	    	
	    	Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
	    	
	    	if(employee.isPresent()) {
	    		employeeService.deleteEmployee(employeeId);
	    		return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);
	    	}else {
	    		return new ResponseEntity<String>("Employee not found with Id:" + employeeId, HttpStatus.NOT_FOUND);
	    	}
}
	    
}
