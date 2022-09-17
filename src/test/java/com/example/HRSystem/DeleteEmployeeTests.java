package com.example.HRSystem;

import com.example.HRSystem.enums.Gender;
import com.example.HRSystem.models.Employee;
import com.example.HRSystem.repositories.DepartmentRepository;
import com.example.HRSystem.repositories.EmployeeRepository;
import com.example.HRSystem.repositories.TeamRepository;
import org.junit.jupiter.api.Assertions;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @BeforeAll
    public static void init() {
    }

    @Test
    public void testDeleteEmployeeNotAManager() throws Exception {
        String str1 = "2015-03-31";
        String str2 = "2023-03-31";
        java.sql.Date date1 = java.sql.Date.valueOf(str1);
        java.sql.Date date2 = java.sql.Date.valueOf(str2);
        Employee toBeDeletedEmployee = Employee.builder()
                .national(5555)
                .name("ezz")
                .gender(Gender.MALE)
                .birthDate(date1)
                .gradDate(date2)
                .grossSalary(3000000)
                .isManager(false)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(null)
                .build();
        Assertions.assertNotNull(toBeDeletedEmployee);
        Employee savedEmp = employeeRepository.save(toBeDeletedEmployee);
        List<Employee> listOfEmployees = savedEmp.getEmployeesList();

        this.mockMvc.perform(delete("/employees/{id}", savedEmp.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        if (!savedEmp.isManager()) {
            Assertions.assertFalse(employeeRepository.existsById(savedEmp.getId()));
        }

    }

    @Test
    public void testDeleteARootManager() throws Exception {
        String str1 = "2015-03-31";
        String str2 = "2023-03-31";
        java.sql.Date date1 = java.sql.Date.valueOf(str1);
        java.sql.Date date2 = java.sql.Date.valueOf(str2);
        Employee toBeDeletedEmployee = Employee.builder()
                .national(5556)
                .name("president")
                .gender(Gender.MALE)
                .birthDate(date1)
                .gradDate(date2)
                .grossSalary(3000000)
                .isManager(true)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(null)
                .build();
        Assertions.assertNotNull(toBeDeletedEmployee);
        Employee savedEmp = employeeRepository.save(toBeDeletedEmployee);

        this.mockMvc.perform(delete("/employees/{id}", savedEmp.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertTrue(employeeRepository.existsById(savedEmp.getId()));

    }

    @Test
    public void testDeleteAManagerHavingManagers() throws Exception {
        String str1 = "2015-03-31";
        String str2 = "2023-03-31";
        java.sql.Date date1 = java.sql.Date.valueOf(str1);
        java.sql.Date date2 = java.sql.Date.valueOf(str2);
        List<Employee> list=new ArrayList<Employee>();
        list.add(employeeRepository.findEmployeeById(1));
        Employee toBeDeletedEmployee = Employee.builder()
                .national(5556)
                .name("president")
                .gender(Gender.MALE)
                .birthDate(date1)
                .gradDate(date2)
                .grossSalary(3000000)
                .isManager(true)
                .employeesList(list)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(employeeRepository.findEmployeeById(3))
                .build();

        Assertions.assertNotNull(toBeDeletedEmployee);

        Employee savedEmp = employeeRepository.save(toBeDeletedEmployee);
        List<Employee> listOfEmployees = savedEmp.getEmployeesList();
        Employee manager = savedEmp.getManager();

        Assertions.assertFalse(employeeRepository.existsById(savedEmp.getId()));
        Assertions.assertTrue(manager.getEmployeesList().contains(listOfEmployees));
        Assertions.assertFalse(manager.getEmployeesList().contains(savedEmp));

    }
}
