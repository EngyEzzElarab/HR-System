package com.example.HRSystem.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryDTO {
    private Double grossSalary;
    private Double netSalary;
}
