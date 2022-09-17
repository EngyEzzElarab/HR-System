package com.example.HRSystem.repositories;

import com.example.HRSystem.models.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team,Integer> {
    Team findTeamById(Integer teamId);
}
