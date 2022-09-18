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
    final String FIND_REC_QUERY = "WITH RECURSIVE SUBORDINATES AS (SELECT * FROM hr.employee WHERE manager_id = ?1 UNION SELECT E.* FROM hr.employee  E INNER JOIN SUBORDINATES S ON E.manager_id = S.id) SELECT * FROM SUBORDINATES;";

    Employee findEmployeeByNational(int i);

    Employee findEmployeeById(Integer managerId);

    List<Employee> findByTeam(Team team);

    List<Employee> findByManager(Employee manager);

    @Query(value = FIND_REC_QUERY, nativeQuery = true)
    List<Employee> findByManagerRec(Integer managerId);

    @Modifying
    @Query("update Employee E set E.manager = ?1 where E.manager = ?2")
    void updateManagerIdForDeletion(Employee manager, Employee employeeToDelete);
}
