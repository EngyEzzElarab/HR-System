package com.example.HRSystem;

import com.example.HRSystem.enums.Gender;
import com.example.HRSystem.models.Employee;
import com.example.HRSystem.repositories.DepartmentRepository;
import com.example.HRSystem.repositories.EmployeeRepository;
import com.example.HRSystem.repositories.TeamRepository;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class DeleteEmployeeTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MockMvc mockMvc;
    private static Date birthDate;
    private static Date graduationDate;
    private Employee toBeDeletedEmployee;


    @BeforeEach
    public void init() {
        Calendar calender = Calendar.getInstance();
        calender.set(2015, 3, 31);
        birthDate = calender.getTime();
        calender.set(2015, 3, 31);
        graduationDate = calender.getTime();
        toBeDeletedEmployee = Employee.builder()
                .national(5555)
                .name("ezz")
                .gender(Gender.MALE)
                .birthDate(birthDate)
                .gradDate(graduationDate)
                .grossSalary(3000000)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .employeesList(null)
                .manager(null)
                .build();
    }

    @Test
    @Transactional
    public void testDeleteEmployeeNotAManager() throws Exception {
        toBeDeletedEmployee.setManager(employeeRepository.findEmployeeById(3));
        Employee savedEmp = employeeRepository.save(toBeDeletedEmployee);
        this.mockMvc.perform(delete("/employees/{id}", savedEmp.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertFalse(employeeRepository.existsById(savedEmp.getId()));
    }

    @Test
    @Transactional
    public void testDeleteARootManager() throws Exception {
        List<Employee> list = new ArrayList<Employee>();
        list.add(employeeRepository.findEmployeeById(1));
        toBeDeletedEmployee.setEmployeesList(list);
        Employee savedEmp = employeeRepository.save(toBeDeletedEmployee);
        this.mockMvc.perform(delete("/employees/{id}", savedEmp.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertTrue(employeeRepository.existsById(savedEmp.getId()));
    }

    @Test
    @Transactional
    public void testDeleteAManagerHavingManagers() throws Exception {
        List<Employee> list = new ArrayList<Employee>();
        list.add(employeeRepository.findEmployeeById(1));
        toBeDeletedEmployee.setManager(employeeRepository.findEmployeeById(3));
        Employee savedEmp = employeeRepository.save(toBeDeletedEmployee);
        List<Employee> listOfEmployees = savedEmp.getEmployeesList();
        Employee manager = savedEmp.getManager();

        Assertions.assertFalse(employeeRepository.existsById(savedEmp.getId()));
        Assertions.assertTrue(manager.getEmployeesList().contains(listOfEmployees));
        Assertions.assertFalse(manager.getEmployeesList().contains(savedEmp));

    }
}
