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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public EmployeeDTO addEmployee(EmployeeCommand employeeCommand) {
        ModelMapper modelMapper = new ModelMapper();
        Employee employee = modelMapper.map(employeeCommand, Employee.class);
        employee.setDepartment(departmentRepository.findDepartmentById(employeeCommand.getDepartmentId()));
        employee.setTeam(teamRepository.findTeamById(employeeCommand.getTeamId()));
        employee.setManager(employeeRepository.findEmployeeById((employeeCommand.getManagerId())));
        employee.setIsManager(employeeCommand.isManager());
        Employee e = employeeRepository.save(employee);
        EmployeeDTO eDTO = modelMapper.map(e, EmployeeDTO.class);
        return eDTO;
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, UpdateEmployeeCommand updateEmployeeCommand) {
        Employee oldEmployee = employeeRepository.findById(id).get();
        ModelMapper modelMapper = new ModelMapper();
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
        return eDTO;

    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findEmployeeById(id);

        if (employee.isManager() == false) {
            employeeRepository.deleteById(id);
        } else {
            if (employee.getManager() == null) {
                System.out.println("CAN NOT DELETE THIS MANAGER");
            } else {
                Employee manager = employee.getManager();
                List<Employee> oldManagerListOfEmp = manager.getEmployeesList();
                oldManagerListOfEmp.addAll(employee.getEmployeesList());
                oldManagerListOfEmp.remove(employee);
                manager.setEmployeesList(oldManagerListOfEmp);
                employeeRepository.deleteById(id);
            }
        }
    }

    @Override
    public EmployeeDTO getEmployee(Integer id) {
        ModelMapper modelMapper = new ModelMapper();
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
        ModelMapper modelMapper = new ModelMapper();
        Team team = teamRepository.findTeamById(teamId);
        List<Employee> listOfEmployees = employeeRepository.findByTeam(team);
        List<EmployeeDTO> listOfEmployeeDTOs = new ArrayList<>();
        for (int i = 0; i < listOfEmployees.size(); i++) {
            EmployeeDTO employeeDTO = modelMapper.map(listOfEmployees.get(i), EmployeeDTO.class);
            employeeDTO.setTeamId(listOfEmployees.get(i).getTeam().getId());
            listOfEmployeeDTOs.add(employeeDTO);
        }
        return listOfEmployeeDTOs;
    }

    @Override
    public List<EmployeeDTO> getEmployeesDirectlyUnderAManager(Integer id) {
        ModelMapper modelMapper = new ModelMapper();
        Employee manager = employeeRepository.findEmployeeById(id);
        List<Employee> listOfEmployees = employeeRepository.findByManager(manager);
        return convertListManager(modelMapper, listOfEmployees);
    }

    @Override
    public List<EmployeeDTO> getEmployeesUnderAManagerRec(Integer id) {
        ModelMapper modelMapper = new ModelMapper();
        Employee manager = employeeRepository.findEmployeeById(id);
        List<Employee> listOfEmployees = employeeRepository.findByManagerRec(manager);
        listOfEmployees.remove(0);
        List<EmployeeDTO> list = convertListManager(modelMapper,listOfEmployees);
        return list;
    }

    private List<EmployeeDTO> convertListManager(ModelMapper modelMapper, List<Employee> listOfEmployees) {
        List<EmployeeDTO> listOfEmployeeDTOs = new ArrayList<>();
        for (int i = 0; i < listOfEmployees.size(); i++) {
            EmployeeDTO employeeDTO = modelMapper.map(listOfEmployees.get(i), EmployeeDTO.class);
            employeeDTO.setManagerId(listOfEmployees.get(i).getManager().getId());
            listOfEmployeeDTOs.add(employeeDTO);
        }
        return listOfEmployeeDTOs;
    }
}
