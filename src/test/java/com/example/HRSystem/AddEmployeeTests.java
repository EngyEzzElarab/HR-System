package com.example.HRSystem;

import com.example.HRSystem.commands.EmployeeCommand;
import com.example.HRSystem.enums.Gender;
import com.example.HRSystem.models.Employee;
import com.example.HRSystem.repositories.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class AddEmployeeTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    private static EmployeeCommand newEmployeeCommand;
    @BeforeEach
    public void initEach() {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.set(2001, Calendar.FEBRUARY, 05);
        Date birthDate = birthCalendar.getTime();
        Calendar gradCalendar = Calendar.getInstance();
        gradCalendar.set(2023, Calendar.MARCH, 31);
        Date gradDate = gradCalendar.getTime();

        newEmployeeCommand = EmployeeCommand.builder()
                .nationalId(5001)
                .name("ezz")
                .gender(Gender.MALE)
                .birthDate(birthDate)
                .gradDate(gradDate)
                .teamId(1)
                //.managerId(100)
                .grossSalary(16000.0)
                .departmentId(1)
                .build();
    }

    @Test
    @Transactional
    @DatabaseSetup("/dataset/addEmployeeScenario.xml")
    public void testAddEmployeeWithAllFields() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeAsString = objectMapper.writeValueAsString(newEmployeeCommand);
        this.mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("ezz")))
                .andExpect(jsonPath("$.gender", is("MALE")))
                .andExpect(jsonPath("$.birthDate", is("2001-02-05")))
                .andExpect(jsonPath("$.gradDate", is("2023-03-31")))
                .andExpect(jsonPath("$.departmentId", is(1)))
                .andExpect(jsonPath("$.teamId", is(1)));
                //.andExpect(jsonPath("$.managerId", is(100)));
        Assertions.assertNotNull(employeeRepository.findEmployeeByNational(5001));
    }

    @Test
    @Transactional
    @DatabaseSetup("/dataset/addEmployeeScenario.xml")
    public void testAddEmployeeWithoutName() throws Exception {
        newEmployeeCommand.setName(null);
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeAsString = objectMapper.writeValueAsString(newEmployeeCommand);

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeAsString));
        });
    }

    @Test
    @Transactional
    @DatabaseSetup("/dataset/addEmployeeScenario.xml")
    public void testAddEmployeeWithInvalidDepartment() throws Exception {
        newEmployeeCommand.setDepartmentId(3);
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeAsString = objectMapper.writeValueAsString(newEmployeeCommand);

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeAsString));
        });
    }

    @Test
    @DatabaseSetup("/dataset/addEmployeeScenario.xml")
    public void testAddEmployeeWithInvalidTeam() throws Exception {
        newEmployeeCommand.setTeamId(3);
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeAsString = objectMapper.writeValueAsString(newEmployeeCommand);

        assertThrows(Exception.class, () -> {
            this.mockMvc.perform(post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeAsString));
        });
    }

}
