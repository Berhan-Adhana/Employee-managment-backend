package org.shama.jenkinsdemo.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.shama.jenkinsdemo.models.Employee;
import org.shama.jenkinsdemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository emp;


    @PostConstruct
    public void init() {
        System.out.println("Proxy class: " + emp.getClass().getName());
    }

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.emp = employeeRepository;
    }

    @Override
    public Employee getEmployee(Long id) {
        return emp.findById(id).orElseThrow(()->new RuntimeException("Employee not found")) ;
    }

    @Transactional
    @Override
    public List<Employee> getAllEmployees() {
        return emp.findAll();
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return emp.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        //check if exits
        Employee savedEmployee = emp.findById(employee.getId()).orElseThrow(() -> new RuntimeException("Employee not found"));
       savedEmployee.setName(employee.getName());
       savedEmployee.setAddress(employee.getAddress());
       savedEmployee.setAge(employee.getAge());
       emp.save(savedEmployee);

//
return savedEmployee;
    }

    @Override
    public void deleteEmployee(Long id) {
    emp.deleteById(id);
    }
}
