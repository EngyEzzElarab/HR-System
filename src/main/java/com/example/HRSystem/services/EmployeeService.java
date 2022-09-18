package com.example.HRSystem.services;

import com.example.HRSystem.commands.EmployeeCommand;
import com.example.HRSystem.commands.UpdateEmployeeCommand;
import com.example.HRSystem.dtos.EmployeeDTO;
import com.example.HRSystem.dtos.EmployeeSalaryDTO;
import com.example.HRSystem.models.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
     EmployeeDTO addEmployee(EmployeeCommand command);

     EmployeeDTO updateEmployee(Integer id, UpdateEmployeeCommand updateEmployeeCommand);

     String deleteEmployee(Integer id);

     EmployeeDTO getEmployee(Integer id);

    EmployeeSalaryDTO getEmployeeSalaryInfo(Integer id);

    List<EmployeeDTO> getEmployeesInATeam(Integer teamId);

    List<EmployeeDTO> getEmployeesDirectlyUnderAManager(Integer id);

    List<EmployeeDTO> getEmployeesUnderAManagerRec(Integer id);
}
