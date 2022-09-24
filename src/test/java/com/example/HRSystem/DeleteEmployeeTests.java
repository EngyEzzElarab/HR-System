package com.example.HRSystem;

import com.example.HRSystem.enums.Gender;
import com.example.HRSystem.models.Employee;
import com.example.HRSystem.repositories.DepartmentRepository;
import com.example.HRSystem.repositories.EmployeeRepository;
import com.example.HRSystem.repositories.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import lombok.extern.java.Log;
import org.assertj.core.internal.Arrays;
import org.assertj.core.util.Lists;
import org.hamcrest.collection.IsArrayContainingInOrder;
import org.junit.jupiter.api.Assertions;

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
import static org.hamcrest.Matchers.contains;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Log
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class DeleteEmployeeTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DatabaseSetup("/dataset/deleteEmployeeScenario.xml")
    @Transactional
    public void testDeleteEmployeeNotAManager() throws Exception {
        this.mockMvc.perform(delete("/employees/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertNull(employeeRepository.findEmployeeByNational(1002));
    }

    @Test
    @Transactional
    @DatabaseSetup("/dataset/deleteEmployeeScenario.xml")
    public void testDeleteARootManager() throws Exception {
        Throwable exception = assertThrows(Exception.class, () -> {
            this.mockMvc.perform(delete("/employees/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        });
        assertTrue(exception.getMessage().contains("can not delete this root manager"));
    }

    @Test
    @Transactional
    @DatabaseSetup("/dataset/deleteEmployeeScenario.xml")
    public void testDeleteAManagerHavingManagers() throws Exception {
        Employee employee = employeeRepository.findEmployeeById(2);
        List<Employee> listOfEmployees = employee.getEmployeesList();
        this.mockMvc.perform(delete("/employees/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Employee manager = employeeRepository.findEmployeeById(1);
        Assertions.assertFalse(employeeRepository.existsById(2));
        Assertions.assertTrue(manager.getEmployeesList().containsAll(listOfEmployees));
    }
}
//log.info("ListOfEmployees "+ objectMapper.writeValueAsString(listOfEmployees));
