package com.junitguide.springboottesting.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junitguide.springboottesting.exception.ResourceNotFoundException;
import com.junitguide.springboottesting.model.Employee;
import com.junitguide.springboottesting.repository.EmployeeRepository;
import com.junitguide.springboottesting.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	
    @Mock
    private EmployeeRepository employeeRepository;
    
    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;
    
    private Employee employee;
    
    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .id(1L)
                .firstName("Komal")
                .lastName("Pawar")
                .email("kopawar@gmail,com")
                .build();
    }
    
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
    
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        Employee savedEmployee = employeeServiceImpl.saveEmployee(employee);
        
        assertThat(savedEmployee).isNotNull();
    }
    
    
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        assertThrows(ResourceNotFoundException.class, () -> {
        	employeeServiceImpl.saveEmployee(employee);
        });

        verify(employeeRepository, never()).save(any(Employee.class));
    }
    
    @DisplayName("JUnit test for getAllEmployyes")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
        
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Sonal")
                .lastName("Pawar")
                .email("sonalpawar@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

        List<Employee> employeeList = employeeServiceImpl.getAllEmployees();

    
        assertThat(employeeList).isNotNull().hasSize(2);
    }
    
    @DisplayName("JUnit test for getAllEmployyes in case of exception")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnException() throws Exception{
        
        given(employeeRepository.findAll()).willThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> {
        	 employeeServiceImpl.getAllEmployees();
        });
       
    }
    
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
        // given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when
        Employee employeeDetail = employeeServiceImpl.getEmployeeById(employee.getId()).get();

        // then
        assertThat(employeeDetail).isNotNull();
        assertEquals("Komal", employeeDetail.getFirstName());

    }
    
 
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ram@gmail.com");
        employee.setFirstName("Ram");
        // when -  action or the behaviour that we are going test
        Employee updatedEmployee = employeeServiceImpl.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }
    
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        // given - precondition or setup
        long employeeId = 1L;

        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when -  action or the behaviour that we are going test
        employeeServiceImpl.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

        

 }


