package com.example.HRSystem.commands;

import com.example.HRSystem.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeCommand {
    private Integer nationalId;
    private String name;
    private Gender gender;
    private Date birthDate;
    private Date gradDate;
    private double grossSalary;
    private Integer managerId;
    private Integer departmentId;
    private Integer teamId;
}
