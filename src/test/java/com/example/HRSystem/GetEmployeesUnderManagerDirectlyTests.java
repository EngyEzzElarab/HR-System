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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class GetEmployeesUnderManagerDirectlyTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetEmployeesDirectlyUnderAManager() throws Exception {
        Calendar calender = Calendar.getInstance();
        calender.set(2015, 3, 31);
        Date birthDate = calender.getTime();
        calender.set(2015, 3, 31);
        Date graduationDate = calender.getTime();
        Employee firstEmployee = Employee.builder()
                .national(3)
                .name("khaled")
                .grossSalary(10000)
                .birthDate(birthDate)
                .gradDate(graduationDate)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(null)
                .gender(Gender.MALE)
                .build();
        Employee manager = employeeRepository.save(firstEmployee);
        Employee secondEmployee = Employee.builder()
                .national(4)
                .name("amany")
                .grossSalary(15000)
                .birthDate(birthDate)
                .gradDate(graduationDate)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(manager)
                .gender(Gender.FEMALE)
                .build();
        Employee thirdEmployee = Employee.builder()
                .national(5)
                .name("omar")
                .grossSalary(15000)
                .birthDate(birthDate)
                .gradDate(graduationDate)
                .department(departmentRepository.findDepartmentById(1))
                .team(teamRepository.findTeamById(1))
                .manager(manager)
                .gender(Gender.MALE)
                .build();
        employeeRepository.save(secondEmployee);
        employeeRepository.save(thirdEmployee);
        this.mockMvc.perform(get("/employees/managers/{id}", manager.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].managerId", is(manager.getId())))
                .andExpect(jsonPath("$[1].managerId", is(manager.getId())))
        ;

    }
}
