package com.example.HRSystem.dtos;

import com.example.HRSystem.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class EmployeeDTO {
    private String name;
    private Gender gender;
    private Date birthDate;
    private Date gradDate;
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


}
