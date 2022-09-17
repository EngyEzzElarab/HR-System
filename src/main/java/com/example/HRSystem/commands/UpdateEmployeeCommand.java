package com.example.HRSystem.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeCommand {
    private boolean isManager;
    private Integer teamId;
    private Integer departmentId;
    private Integer managerId;
    private Integer grossSalary;

    public boolean isManager() {
        return isManager;
    }

    public void setIsManager(boolean manager) {
        isManager = manager;
    }
}
