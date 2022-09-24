package com.example.HRSystem.commands;

import com.example.HRSystem.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    //@JsonFormat(timezone = "Africa/Cairo", pattern = "yyyy-mm-dd")
    private Date birthDate;
    //@JsonFormat(timezone = "Africa/Cairo", pattern = "yyyy-mm-dd")
    private Date gradDate;
    private double grossSalary;
    private Integer managerId;
    private Integer departmentId;
    private Integer teamId;
}
