package com.example.HRSystem;

import com.example.HRSystem.commands.EmployeeCommand;
import com.example.HRSystem.enums.Gender;
import com.example.HRSystem.repositories.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AddEmployeeTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testAddEmployeeWithAllFields() throws Exception {

        String str1 = "2015-03-31";
        String str2 = "2023-03-31";
        java.sql.Date date1 = java.sql.Date.valueOf(str1);
        java.sql.Date date2 = java.sql.Date.valueOf(str2);
        EmployeeCommand newEmp = new EmployeeCommand();
        newEmp.setDepartmentId(1);
        newEmp.setName("ezz");
        newEmp.setGender(Gender.MALE);
        newEmp.setBirthDate(date1);
        newEmp.setGradDate(date2);
        newEmp.setIsManager(true);
        newEmp.setNationalId(1200);
        newEmp.setTeamId(1);
        newEmp.setManagerId(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeAsString = objectMapper.writeValueAsString(newEmp);
        this.mockMvc.perform(post("/employees/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("ezz")))
                .andExpect(jsonPath("$.gender", is("MALE")))
                .andExpect(jsonPath("$.birthDate", is("2015-03-31")))
                .andExpect(jsonPath("$.gradDate", is("2023-03-31")))
                .andExpect(jsonPath("$.isManager", is(true)))
                .andExpect(jsonPath("$.departmentId", is(1)))
                .andExpect(jsonPath("$.teamId", is(1)))
                .andExpect(jsonPath("$.managerId", is(1)));


        Assertions.assertNotNull(employeeRepository.findEmployeeByNational(1200));
    }

    @Test
    public void testAddEmployeeWithoutName() throws Exception {
        String str1 = "2015-03-31";
        String str2 = "2023-03-31";
        java.sql.Date date1 = java.sql.Date.valueOf(str1);
        java.sql.Date date2 = java.sql.Date.valueOf(str2);
        EmployeeCommand newEmp = new EmployeeCommand();
        newEmp.setGender(Gender.FEMALE);
        newEmp.setBirthDate(date1);
        newEmp.setGradDate(date2);
        newEmp.setIsManager(false);
        newEmp.setNationalId(1259);
        newEmp.setDepartmentId(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeAsString = objectMapper.writeValueAsString(newEmp);

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(post("/employees/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeAsString));
        });
    }

    @Test
    public void testAddEmployeeWithInvalidDepartment() throws Exception {
        String str1 = "2015-03-31";
        String str2 = "2023-03-31";
        java.sql.Date date1 = java.sql.Date.valueOf(str1);
        java.sql.Date date2 = java.sql.Date.valueOf(str2);
        EmployeeCommand newEmp = new EmployeeCommand();
        newEmp.setDepartmentId(2);
        newEmp.setGender(Gender.FEMALE);
        newEmp.setBirthDate(date1);
        newEmp.setGradDate(date2);
        newEmp.setIsManager(false);
        newEmp.setNationalId(1251);
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeAsString = objectMapper.writeValueAsString(newEmp);

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(post("/employees/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeAsString));
        });
    }

    @Test
    public void testAddEmployeeWithInvalidTeam() throws Exception {
        String str1 = "2015-03-31";
        String str2 = "2023-03-31";
        java.sql.Date date1 = java.sql.Date.valueOf(str1);
        java.sql.Date date2 = java.sql.Date.valueOf(str2);
        EmployeeCommand newEmp = new EmployeeCommand();
        newEmp.setDepartmentId(1);
        newEmp.setTeamId(2);
        newEmp.setGender(Gender.MALE);
        newEmp.setBirthDate(date1);
        newEmp.setGradDate(date2);
        newEmp.setIsManager(false);
        newEmp.setNationalId(2);
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeAsString = objectMapper.writeValueAsString(newEmp);

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(post("/employees/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeAsString));
        });
    }

}
