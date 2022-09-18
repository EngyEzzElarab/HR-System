package com.example.HRSystem.repositories;

import com.example.HRSystem.models.Employee;
import com.example.HRSystem.models.Team;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findEmployeeByNational(int i);

    Employee findEmployeeById(Integer managerId);

    List<Employee> findByTeam(Team team);

    List<Employee> findByManager(Employee manager);

    String query1 = "WITH RECURSIVE SUBORDINATES AS (SELECT * FROM hr.employee WHERE manager_id = ?1 UNION SELECT E.* FROM hr.employee  E INNER JOIN SUBORDINATES S ON E.manager_id = S.id) SELECT * FROM SUBORDINATES;";

    @Query(value = query1, nativeQuery = true)
    List<Employee> findByManagerRec(Integer managerId);

    String query2 = "UPDATE hr.employee SET  manager_id = :managerId WHERE manager_id = :employeeId ";
@Modifying
    @Query(value = query2, nativeQuery = true)
    void updateManagerIdForDeletion(@Param("managerId") Integer managerId,@Param("employeeId") Integer employeeId);
}
