package com.example.HRSystem.controllers;

import com.example.HRSystem.commands.EmployeeCommand;
import com.example.HRSystem.commands.UpdateEmployeeCommand;
import com.example.HRSystem.dtos.EmployeeDTO;
import com.example.HRSystem.dtos.EmployeeSalaryDTO;
import com.example.HRSystem.models.Department;
import com.example.HRSystem.models.Employee;
import com.example.HRSystem.repositories.DepartmentRepository;
import com.example.HRSystem.repositories.EmployeeRepository;
import com.example.HRSystem.repositories.TeamRepository;
import com.example.HRSystem.services.EmployeeService;
import com.example.HRSystem.services.EmployeeServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//import javax.validation.Valid;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(path = "/add")
    public EmployeeDTO addNewEmployee(@Valid @RequestBody EmployeeCommand employeeCommand) {
        return employeeService.addEmployee(employeeCommand);
    }


    @PutMapping(path = "/{id}")
    public EmployeeDTO modifyEmployee(@PathVariable Integer id, @RequestBody UpdateEmployeeCommand updateEmployeeCommand)
    {
        return employeeService.updateEmployee(id,updateEmployeeCommand);
    }

    @DeleteMapping(path="/{id}")
    public void delete(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }
    @GetMapping(path = "/{id}")
    public EmployeeDTO getEmployee(@PathVariable Integer id)
    {
        return employeeService.getEmployee(id);
    }
    @GetMapping(path = "/{id}/salary")
    public EmployeeSalaryDTO getEmployeeSalaryInfo(@PathVariable Integer id)
    {
        return employeeService.getEmployeeSalaryInfo(id);
    }
    @GetMapping(path = "/teams/{id}")
    public List<EmployeeDTO> getEmployeesInATeam(@PathVariable Integer id)
    {
        return employeeService.getEmployeesInATeam(id);
    }
    @GetMapping(path = "/managers/{id}")
    public List<EmployeeDTO> getEmployeesDirectlyUnderAManager(@PathVariable Integer id)
    {
        return employeeService.getEmployeesDirectlyUnderAManager(id);
    }
    @GetMapping(path = "/managers/{id}/all")
    public List<EmployeeDTO> getEmployeesUnderAManagerRec(@PathVariable Integer id)
    {
        return employeeService.getEmployeesUnderAManagerRec(id);
    }
}
