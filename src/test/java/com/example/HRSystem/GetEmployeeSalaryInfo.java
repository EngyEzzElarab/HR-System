package com.example.HRSystem;

import com.example.HRSystem.enums.Gender;
import com.example.HRSystem.models.Employee;
import com.example.HRSystem.repositories.DepartmentRepository;
import com.example.HRSystem.repositories.EmployeeRepository;
import com.example.HRSystem.repositories.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class GetEmployeeSalaryInfo {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    public void testGetEmployeeSalaryInfo() throws Exception {
        String str1 = "2015-03-31";
        String str2 = "2023-03-31";
        java.sql.Date date1 = java.sql.Date.valueOf(str1);
        java.sql.Date date2 = java.sql.Date.valueOf(str2);
        Employee e = Employee.builder()
                .national(1200)
                .name("mohsen")
                .grossSalary(1000)
                .birthDate(date1)
                .gradDate(date2)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(null)
                .isManager(true)
                .gender(Gender.MALE)
                .build();
        Employee savedEmployee = employeeRepository.save(e);
        this.mockMvc.perform(get("/employees/{id}/salary", savedEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                 .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.grossSalary", is(1000.0)))
                .andExpect(jsonPath("$.netSalary", is(350.0)));

    }
}
