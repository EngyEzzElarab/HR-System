package com.example.HRSystem.models;

import com.example.HRSystem.enums.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "employee")
//@Data
//@Setter
//@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, name = "national_id")
    private Integer national;
    @NotBlank(message = "Employee Name Should Be Added")
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private Date birthDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "grad_date")
    private Date gradDate;
    @Column(name = "gross_salary")
    private double grossSalary;
    @Transient
    @Column(name = "net_salary")
    @Formula("(grossSalary * 0.85) - 500")
    private double netSalary;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    @JsonBackReference
    @OneToMany(mappedBy = "manager")
    private List<Employee> employeesList;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Integer getNational() {
        return national;
    }

    public void setNational(Integer national) {
        this.national = national;
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

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }

    public Employee getManager() {
        return manager;
    }

    public Integer getId() {
        return id;
    }

}
