package com.example.HRSystem;

import com.example.HRSystem.commands.UpdateEmployeeCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
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

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;
import static org.hamcrest.Matchers.nullValue;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class ModifyEmployeeTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DatabaseSetup("/dataset/modifyEmployeeScenario.xml")
    public void modifyingEmployeeWithAllAttributesTest() throws Exception {
        UpdateEmployeeCommand updatedEmployee =
                UpdateEmployeeCommand.builder()
                        .departmentId(2)
                        .teamId(2)
                        .managerId(1)
                        .build();
        String employeeAsString = objectMapper.writeValueAsString(updatedEmployee);
        mockMvc.perform(put("/employees/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.managerId", is(1)))
                .andExpect(jsonPath("$.departmentId", is(2)))
                .andExpect(jsonPath("$.teamId", is(2)))
        ;
    }

    @Test
    @DatabaseSetup("/dataset/modifyEmployeeScenario.xml")
    public void modifyingEmployeeWithWrongIdTest() throws Exception {
        UpdateEmployeeCommand updatedEmployee =
                UpdateEmployeeCommand.builder()
                        .departmentId(2)
                        .teamId(2)
                        .managerId(1)
                        .build();
        String employeeAsString = objectMapper.writeValueAsString(updatedEmployee);
        assertThrows(Exception.class, () -> {
            mockMvc.perform(put("/employees/{id}", 5)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeAsString));
                });

    }
}
