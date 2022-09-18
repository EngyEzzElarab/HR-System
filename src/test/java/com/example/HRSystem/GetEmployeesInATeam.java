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

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.*;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class GetEmployeesInATeam {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetEmployeesInATeam() throws Exception {
        Calendar calender = Calendar.getInstance();
        calender.set(2015, 3, 31);
        Date birthDate = calender.getTime();
        calender.set(2015, 3, 31);
        Date graduationDate = calender.getTime();
        Employee e1 = Employee.builder()
                .national(3)
                .name("engy")
                .grossSalary(10000)
                .birthDate(birthDate)
                .gradDate(graduationDate)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(null)
                .gender(Gender.FEMALE)
                .build();
        Employee e2 = Employee.builder()
                .national(4)
                .name("amany")
                .grossSalary(15000)
                .birthDate(birthDate)
                .gradDate(graduationDate)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(null)
                .gender(Gender.FEMALE)
                .build();
        employeeRepository.save(e1);
        employeeRepository.save(e2);
        this.mockMvc.perform(get("/employees/teams/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].teamId", is(1)))
                .andExpect(jsonPath("$[1].teamId", is(1)))
        ;
    }

}
