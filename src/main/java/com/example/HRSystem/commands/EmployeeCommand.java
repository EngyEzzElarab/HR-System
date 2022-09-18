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

    public EmployeeCommand(Integer nationalId, String name, Gender gender, java.sql.Date birthDate, java.sql.Date gradDate, double grossSalary, Integer managerId, Integer departmentId, Integer teamId) {
        this.name = name;
        this.birthDate = birthDate;
        this.gradDate = gradDate;
        this.grossSalary = grossSalary;
        this.gender = gender;
        this.nationalId = nationalId;
        this.managerId = managerId;
        this.departmentId = departmentId;
        this.teamId = teamId;
    }

    public Integer getNationalId() {
        return nationalId;
    }

    public void setNationalId(Integer nationalId) {
        this.nationalId = nationalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getGradDate() {
        return gradDate;
    }

    public void setGradDate(Date gradDate) {
        this.gradDate = gradDate;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }
    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

}
