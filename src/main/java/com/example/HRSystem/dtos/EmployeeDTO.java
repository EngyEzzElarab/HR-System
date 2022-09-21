package com.example.HRSystem.dtos;

import com.example.HRSystem.enums.Gender;
import com.example.HRSystem.models.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeDTO {
    private String name;
    private Gender gender;
    private Date birthDate;
    private Date gradDate;
    private Integer teamId;
    private Integer departmentId;
    private Integer managerId;

}
