package org.shama.jenkinsdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.shama.jenkinsdemo.models.Employee;
import org.shama.jenkinsdemo.services.EmployeeService;
import org.shama.jenkinsdemo.services.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getEmployee() throws Exception {
        Employee employee = new Employee(1L,"Jhon",20,1);
        Long employeeId = 1L;
        Mockito.when(employeeService.getEmployee(1L)).thenReturn(employee);

        mockMvc.perform(get("/employees/{id}",employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value("Jhon"));
    }

    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L,"Jhon",20,1));
        employees.add(new Employee(2L,"Berhan",20,2));
        employees.add(new Employee(3L,"Selam",20,3));

        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/employees/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employees.size()))
                .andExpect(jsonPath("$[0].name").value(employees.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(employees.get(1).getName()));

    }

    @Test
    void createEmployee() throws Exception {
        Employee employee = new Employee(1L,"Jhon",20,1);

        Mockito.when(employeeService.addEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jhon"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void updateEmployee() throws Exception {
Employee employee = new Employee(1L,"Jhon",20,1);
Mockito.when(employeeService.updateEmployee(Mockito.any(Employee.class))).thenReturn(employee);

mockMvc.perform(put("/employees/",employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(employee.getId()))
        .andExpect(jsonPath("$.name").value("Jhon"));
    }

    @Test
    void deleteEmployee() throws Exception {
Long employeeId = 1L;

Mockito.doNothing().when(employeeService).deleteEmployee(employeeId);

mockMvc.perform(delete("/employees/{id}",employeeId))
        .andExpect(status().isNoContent()); //or.isOk() if you return 200

//        Mockito.when(employeeService.)
    }
}