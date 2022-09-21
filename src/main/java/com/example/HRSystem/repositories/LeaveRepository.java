package com.example.HRSystem.repositories;

import com.example.HRSystem.models.Leave;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends CrudRepository<Leave,Integer> {
}
