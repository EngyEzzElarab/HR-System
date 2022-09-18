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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class GelAllEmployeesUnderAManagerRec {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetAllEmployeesUnderAManagerRec() throws Exception {
        String str1 = "2015-03-31";
        String str2 = "2023-03-31";
        java.sql.Date date1 = java.sql.Date.valueOf(str1);
        java.sql.Date date2 = java.sql.Date.valueOf(str2);
        Employee employee1 = Employee.builder()
                .national(3000)
                .name("khaled")
                .grossSalary(10000)
                .birthDate(date1)
                .gradDate(date2)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(null)
                .gender(Gender.MALE)
                .build();
        Employee rootManager = employeeRepository.save(employee1);
        Employee employee2 = Employee.builder()
                .national(4000)
                .name("amany")
                .grossSalary(12000)
                .birthDate(date1)
                .gradDate(date2)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(rootManager)
                .gender(Gender.FEMALE)
                .build();
        Employee midManager = employeeRepository.save(employee2);
        Employee employee3 = Employee.builder()
                .national(5000)
                .name("omar")
                .grossSalary(15000)
                .birthDate(date1)
                .gradDate(date2)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(midManager)
                .gender(Gender.MALE)
                .build();
        Employee savedEmployee2 = employeeRepository.save(employee3);
        this.mockMvc.perform(get("/employees/managers/{id}/all", rootManager.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].managerId", is(rootManager.getId())))
                .andExpect(jsonPath("$[1].managerId", is(midManager.getId())))
        ;
    }
}
