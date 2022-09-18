package com.example.HRSystem.dtos;

import com.example.HRSystem.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

//@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class EmployeeDTO {
    private String name;
    private Gender gender;
    private java.sql.Date birthDate;
    private java.sql.Date gradDate;
    private boolean isManager;
    private Integer teamId;
    private Integer departmentId;
    private Integer managerId;

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

    public boolean isManager() {
        return isManager;
    }

    public void setIsManager(boolean manager) {
        isManager = manager;
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

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public EmployeeDTO(String name, Gender gender, java.sql.Date birthDate, java.sql.Date gradDate,Integer managerId, Integer departmentId,Integer teamId) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.gradDate = gradDate;
        this.managerId = managerId;
        this.departmentId = departmentId;
        this.teamId = teamId;
    }

}
