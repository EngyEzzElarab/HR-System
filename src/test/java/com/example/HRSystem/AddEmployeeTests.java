package com.example.HRSystem;

import com.example.HRSystem.commands.EmployeeCommand;
import com.example.HRSystem.enums.Gender;
import com.example.HRSystem.models.Employee;
import com.example.HRSystem.repositories.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
public class AddEmployeeTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private  EmployeeRepository employeeRepository;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static EmployeeCommand newEmployeeCommand;
    static final java.sql.Date BIRTH_DATE = java.sql.Date.valueOf("2015-03-31");
    static final java.sql.Date GRADUATION_DATE = java.sql.Date.valueOf("2023-03-31");

    @BeforeEach
    public void initEach() {
        newEmployeeCommand = EmployeeCommand.builder()
                .nationalId(5000)
                .name("ezz")
                .gender(Gender.MALE)
                .birthDate(BIRTH_DATE)
                .gradDate(GRADUATION_DATE)
                .teamId(1)
                .managerId(5)
                .grossSalary(16000.0)
                .departmentId(1)
                .build();
    }

    @Test
    @Transactional
    //@DatabaseSetup("/dataset/employee.xml")
    //TODO fix the format of date
    public void testAddEmployeeWithAllFields() throws Exception {
        String employeeAsString = objectMapper.writeValueAsString(newEmployeeCommand);
        this.mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("ezz")))
                .andExpect(jsonPath("$.gender", is("MALE")))
                //.andExpect(jsonPath("$.birthDate", is("2015-03-31")))
                //.andExpect(jsonPath("$.gradDate", is("2023-03-31")))
                .andExpect(jsonPath("$.departmentId", is(1)))
                .andExpect(jsonPath("$.teamId", is(1)))
                .andExpect(jsonPath("$.managerId", is(5)));

        Assertions.assertNotNull(employeeRepository.findEmployeeByNational(5000));
    }

    @Test
    @Transactional
    public void testAddEmployeeWithoutName() throws Exception {
        newEmployeeCommand.setName(null);
        String employeeAsString = objectMapper.writeValueAsString(newEmployeeCommand);

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeAsString));
        });
    }

    @Test
    @Transactional
    public void testAddEmployeeWithInvalidDepartment() throws Exception {
        newEmployeeCommand.setDepartmentId(3);
        String employeeAsString = objectMapper.writeValueAsString(newEmployeeCommand);

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeAsString));
        });
    }

    @Test
    public void testAddEmployeeWithInvalidTeam() throws Exception {
        newEmployeeCommand.setTeamId(3);
        String employeeAsString = objectMapper.writeValueAsString(newEmployeeCommand);

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeAsString));
        });
    }

}
