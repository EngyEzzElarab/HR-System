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
     EmployeeDTO addEmployee(EmployeeCommand command) throws Exception;

     EmployeeDTO updateEmployee(Integer id, UpdateEmployeeCommand updateEmployeeCommand);

     void deleteEmployee(Integer id) throws Exception;

     EmployeeDTO getEmployee(Integer id) throws Exception;

    EmployeeSalaryDTO getEmployeeSalaryInfo(Integer id) throws Exception;

    List<EmployeeDTO> getEmployeesInATeam(Integer teamId) throws Exception;

    List<EmployeeDTO> getEmployeesDirectlyUnderAManager(Integer id) throws Exception;

    List<EmployeeDTO> getEmployeesUnderAManagerRec(Integer id) throws Exception;
}
