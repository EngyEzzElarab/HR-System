package com.example.HRSystem.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "department_ID")
    @JsonManagedReference
    private Department dept;
    @OneToMany(mappedBy = "team")
    @JsonManagedReference
    private Set<Employee> employees= new HashSet<>();
}