package com.example.HRSystem.dtos;

import com.example.HRSystem.enums.Gender;
import com.example.HRSystem.models.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date birthDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date gradDate;
    private Integer teamId;
    private Integer departmentId;
    private Integer managerId;

}
