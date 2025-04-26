package org.shama.jenkinsdemo.services;

import org.shama.jenkinsdemo.models.Employee;

import java.util.List;

public interface EmployeeService {
    public Employee getEmployee(Long id);
    public List<Employee> getAllEmployees();
    public Employee addEmployee(Employee employee);
    public Employee updateEmployee(Employee employee);
    public void deleteEmployee(Long id);
}
