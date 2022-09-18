package com.example.HRSystem;

import com.example.HRSystem.enums.Gender;
import com.example.HRSystem.models.Employee;
import com.example.HRSystem.models.Team;
import com.example.HRSystem.repositories.DepartmentRepository;
import com.example.HRSystem.repositories.EmployeeRepository;
import com.example.HRSystem.repositories.TeamRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DatabaseSetup("/dataset/Employees.xml")
public class GetEmployeeTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetEmployeeInfo() throws Exception {
        this.mockMvc.perform(get("/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.name", is("khaled")))
                .andExpect(jsonPath("$.gender", is("MALE")))
                .andExpect(jsonPath("$.birthDate", is("2000-09-14")))
                .andExpect(jsonPath("$.gradDate", is("2023-07-01")));
    }
}
