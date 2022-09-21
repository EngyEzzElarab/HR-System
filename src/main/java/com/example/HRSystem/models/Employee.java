package com.example.HRSystem.models;

import com.example.HRSystem.enums.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Data
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE, pattern = "dd-MM-yyyy")
    private Date birthDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "grad_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = JsonFormat.DEFAULT_TIMEZONE, pattern = "dd-MM-yyyy")
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
    @JsonManagedReference
    private Department department;
    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;
    @OneToMany(mappedBy = "employee")
    private List<Leave> leaves;

}
