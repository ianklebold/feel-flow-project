package com.equipo5.feelflowapp.repository.team;

import com.equipo5.feelflowapp.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
}
