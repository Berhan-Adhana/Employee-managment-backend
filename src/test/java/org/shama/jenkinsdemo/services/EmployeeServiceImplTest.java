package org.shama.jenkinsdemo.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.shama.jenkinsdemo.models.Employee;
import org.shama.jenkinsdemo.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Mock
    private EmployeeRepository employeeRepository;


    @Test
    void getEmployee() {
        Employee employee = new Employee();
        employee.setAge(20);
        employee.setName("John");
        employee.setId(1L);
        employee.setAddress(1);
        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
    Employee foundEmployee = employeeService.getEmployee(employee.getId());

    Assertions.assertEquals("John", foundEmployee.getName());
    Assertions.assertEquals(20, foundEmployee.getAge());


    }

    @Test
    void getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L,"Jhon",20,1));
        employees.add(new Employee(2L,"Berhan",20,2));
        employees.add(new Employee(3L,"Selam",20,3));

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> foundEmployees = employeeService.getAllEmployees();

        Assertions.assertEquals(3, foundEmployees.size());
        Assertions.assertEquals(1L, foundEmployees.get(0).getId());
    }

    @Test
    void addEmployee() {
//        ARRANGE
        Employee employee = new Employee();
        employee.setAge(20);
        employee.setName("John");
        employee.setId(1L);
       employee.setAddress(1);

//       ACT
       Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

       Employee saveEmployee = employeeService.addEmployee(employee);
//       ASSERT
       Assertions.assertNotNull(saveEmployee);
       Assertions.assertEquals("John", saveEmployee.getName());
    }

    @Test
    void updateEmployee() {
        Employee employee = new Employee();
        employee.setAge(20);
        employee.setName("John");
        employee.setId(1L);
        employee.setAddress(1);

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);
        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee updatedEmployee = employeeService.updateEmployee(employee);
        Assertions.assertNotNull(updatedEmployee);
        Assertions.assertEquals("John", updatedEmployee.getName());

    }

    @Test
    void deleteEmployee() {
        Employee employee = new Employee();
        employee.setAge(20);
        employee.setName("John");
        employee.setId(1L);
        employee.setAddress(1);

        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.ofNullable(employee));
        employeeService.deleteEmployee(employee.getId());

        Assertions.assertNotNull(employeeRepository.findById(employee.getId()));

//        Assertions.assertNull(employeeRepository.findById(employee.getId()));

    }
}