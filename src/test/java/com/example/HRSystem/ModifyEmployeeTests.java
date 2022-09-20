package com.example.HRSystem;

import com.example.HRSystem.commands.UpdateEmployeeCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;
import static org.hamcrest.Matchers.nullValue;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ModifyEmployeeTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void modifyingEmployeeWithAllAttributesTest() throws Exception {
        UpdateEmployeeCommand updatedEmployee =
                UpdateEmployeeCommand.builder()
                        .departmentId(2)
                        .teamId(2)
                        .managerId(1)
                        .build();
        ObjectMapper objectMapper = new ObjectMapper();
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
        //nullValue()

    }
}
