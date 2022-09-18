package com.example.HRSystem.repositories;

import com.example.HRSystem.models.Employee;
import com.example.HRSystem.models.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findEmployeeByNational(int i);

    Employee findEmployeeById(Integer managerId);

    List<Employee> findByTeam(Team team);

    List<Employee> findByManager(Employee manager);

    String s = "WITH RECURSIVE SUBORDINATES AS (SELECT * FROM hr.employee WHERE manager_id = ?1 UNION SELECT E.* FROM hr.employee  E INNER JOIN SUBORDINATES S ON E.manager_id = S.id) SELECT * FROM SUBORDINATES;";
    @Query(value = s, nativeQuery = true)
    List<Employee> findByManagerRec(Integer managerId);
}
