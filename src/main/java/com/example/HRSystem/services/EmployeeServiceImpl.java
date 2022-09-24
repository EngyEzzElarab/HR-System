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
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
//    @Autowired
//    private PasswordEncoder passwordEncoder;


    final double taxPercentage = 0.85;
    final int insurance = 500;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public EmployeeDTO addEmployee(EmployeeCommand employeeCommand) throws Exception {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Integer managerId = employeeCommand.getManagerId();
        if (managerId != null && employeeRepository.findEmployeeById(employeeCommand.getManagerId()) == null) {
            throw new Exception("There is no such manager!");
        }
        if (departmentRepository.findDepartmentById(employeeCommand.getDepartmentId()) == null) {
            throw new Exception("There is no such department!");
        }
        if (teamRepository.findTeamById(employeeCommand.getTeamId()) == null) {
            throw new Exception("There is no such team!");
        }
        Employee employee = modelMapper.map(employeeCommand, Employee.class);
        employee.setManager(employeeRepository.findEmployeeById((employeeCommand.getManagerId())));
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDTO eDTO = modelMapper.map(savedEmployee, EmployeeDTO.class);
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
        return eDTO;

    }

    @Transactional
    public void deleteTransaction(Employee manager, Employee employee) {
        employeeRepository.updateManagerIdForDeletion(manager, employee);
        employeeRepository.deleteById(employee.getId());
    }

    @Override
    public void deleteEmployee(Integer id) throws Exception {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null)
            throw new Exception("This employee does not exist");


        if (employee.getEmployeesList() == null || employee.getEmployeesList().size() == 0) {
            employeeRepository.deleteById(id);
        } else {
            if (employee.getManager() == null) {
                throw new Exception("can not delete this root manager");
            } else {
                deleteTransaction(employee.getManager(), employee);
            }
        }
    }

    @Override
    public EmployeeDTO getEmployee(Integer id) throws Exception {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null)
            throw new Exception("There is no such employee");
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        if (employee.getManager() != null)
            employeeDTO.setManagerId(employee.getManager().getId());
        return employeeDTO;
    }

    @Override
    public EmployeeSalaryDTO getEmployeeSalaryInfo(Integer id) throws Exception {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null)
            throw new Exception("There is no such employee");
        EmployeeSalaryDTO employeeSalaryDTO = EmployeeSalaryDTO.builder()
                .grossSalary(employee.getGrossSalary())
                .netSalary((employee.getGrossSalary() * taxPercentage) - insurance)
                .build();
        return employeeSalaryDTO;
    }

    @Override
    public List<EmployeeDTO> getEmployeesInATeam(Integer teamId) throws Exception {
        Team team = teamRepository.findTeamById(teamId);
        if (team == null)
            throw new Exception("There is no such team");
        List<Employee> listOfEmployees = employeeRepository.findByTeam(team);
        List<EmployeeDTO> listOfEmployeeDTOs = modelMapper.map(
                listOfEmployees,
                new TypeToken<List<EmployeeDTO>>() {
                }.getType());
        return listOfEmployeeDTOs;
    }

    @Override
    public List<EmployeeDTO> getEmployeesDirectlyUnderAManager(Integer id) throws Exception {
        Employee manager = employeeRepository.findEmployeeById(id);
        if (manager == null)
            throw new Exception("There is no such manager");
        List<Employee> listOfEmployees = employeeRepository.findByManager(manager);
        List<EmployeeDTO> listOfEmployeeDTOs = modelMapper.map(listOfEmployees, new TypeToken<List<EmployeeDTO>>() {
        }.getType());
        return listOfEmployeeDTOs;
    }

    @Override
    public List<EmployeeDTO> getEmployeesUnderAManagerRec(Integer id) throws Exception {
        Employee manager = employeeRepository.findEmployeeById(id);
        if (manager == null)
            throw new Exception("There is no such manager");
        List<Employee> listOfEmployees = employeeRepository.findByManagerRec(id);
        List<EmployeeDTO> listOfEmployeeDTOs = modelMapper.map(listOfEmployees, new TypeToken<List<EmployeeDTO>>() {
        }.getType());
        return listOfEmployeeDTOs;
    }
}
