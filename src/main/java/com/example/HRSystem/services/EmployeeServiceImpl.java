package com.example.HRSystem.services;

import com.example.HRSystem.commands.EmployeeCommand;
import com.example.HRSystem.commands.UpdateEmployeeCommand;
import com.example.HRSystem.dtos.EmployeeDTO;
import com.example.HRSystem.dtos.EmployeeSalaryDTO;
import com.example.HRSystem.models.Employee;
import com.example.HRSystem.models.Team;
import com.example.HRSystem.repositories.DepartmentRepository;
import com.example.HRSystem.repositories.EmployeeRepository;
import com.example.HRSystem.repositories.TeamRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public EmployeeDTO addEmployee(EmployeeCommand employeeCommand) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Employee employee = modelMapper.map(employeeCommand, Employee.class);
        employee.setManager(employeeRepository.findEmployeeById((employeeCommand.getManagerId())));
        Employee e = employeeRepository.save(employee);
        EmployeeDTO eDTO = modelMapper.map(e, EmployeeDTO.class);
        eDTO.setIsManager(employeeCommand.isManager());
        return eDTO;
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, UpdateEmployeeCommand updateEmployeeCommand) {
        Employee oldEmployee = employeeRepository.findById(id).get();
        if (Objects.nonNull(updateEmployeeCommand.getManagerId())) {
            oldEmployee.setManager(employeeRepository.findById(updateEmployeeCommand.getManagerId()).get());
        } else {
            oldEmployee.setManager(null);
        }
        if (Objects.nonNull(updateEmployeeCommand.isManager())) {
            oldEmployee.setIsManager(updateEmployeeCommand.isManager());
        }
        if (Objects.nonNull(updateEmployeeCommand.getTeamId())) {
            oldEmployee.setTeam(teamRepository.findById(updateEmployeeCommand.getTeamId()).get());
        }
        if (Objects.nonNull(updateEmployeeCommand.getDepartmentId())) {
            oldEmployee.setDepartment(departmentRepository.findById(updateEmployeeCommand.getDepartmentId()).get());
        }
        if (Objects.nonNull(updateEmployeeCommand.getGrossSalary())) {
            oldEmployee.setGrossSalary(updateEmployeeCommand.getGrossSalary());
        }
        Employee employeeDB = employeeRepository.save(oldEmployee);
        EmployeeDTO eDTO = modelMapper.map(employeeDB, EmployeeDTO.class);
        if(Objects.nonNull(updateEmployeeCommand.isManager()))
            eDTO.setIsManager(updateEmployeeCommand.isManager());
        return eDTO;

    }

    @Override
    public String deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null)
            return "Tis employee does not exist";


        if (employee.isManager() == false) {
            employeeRepository.deleteById(id);
        } else {
            if (employee.getManager() == null) {
               return "can not delete this root manager";
            } else {
                employeeRepository.updateManagerIdForDeletion(employee.getManager().getId(),id);
                employeeRepository.deleteById(id);
            }
        }
        return "Deleted Successfully";
    }

    @Override
    public EmployeeDTO getEmployee(Integer id) {
        Employee employee = employeeRepository.findEmployeeById(id);
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        return employeeDTO;
    }

    @Override
    public EmployeeSalaryDTO getEmployeeSalaryInfo(Integer id) {
        Employee employee = employeeRepository.findEmployeeById(id);
        EmployeeSalaryDTO employeeSalaryDTO = EmployeeSalaryDTO.builder()
                .grossSalary(employee.getGrossSalary())
                .netSalary((employee.getGrossSalary() * 0.85) - 500)
                .build();
        return employeeSalaryDTO;
    }

    @Override
    public List<EmployeeDTO> getEmployeesInATeam(Integer teamId) {
        Team team = teamRepository.findTeamById(teamId);
        List<Employee> listOfEmployees = employeeRepository.findByTeam(team);
        List<EmployeeDTO> listOfEmployeeDTOs = modelMapper.map(listOfEmployees, new TypeToken<List<EmployeeDTO>>() {
        }.getType());
        return listOfEmployeeDTOs;
    }

    @Override
    public List<EmployeeDTO> getEmployeesDirectlyUnderAManager(Integer id) {
        Employee manager = employeeRepository.findEmployeeById(id);
        List<Employee> listOfEmployees = employeeRepository.findByManager(manager);
        List<EmployeeDTO> listOfEmployeeDTOs = modelMapper.map(listOfEmployees, new TypeToken<List<EmployeeDTO>>() {
        }.getType());
        return listOfEmployeeDTOs;
    }

    @Override
    public List<EmployeeDTO> getEmployeesUnderAManagerRec(Integer id) {
        List<Employee> listOfEmployees = employeeRepository.findByManagerRec(id);
        List<EmployeeDTO> listOfEmployeeDTOs = modelMapper.map(listOfEmployees, new TypeToken<List<EmployeeDTO>>() {
        }.getType());
        return listOfEmployeeDTOs;
    }
}
