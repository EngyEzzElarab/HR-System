package com.example.HRSystem.repositories;

import com.example.HRSystem.models.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department,Integer> {
    Department findDepartmentById(Integer id);
}
