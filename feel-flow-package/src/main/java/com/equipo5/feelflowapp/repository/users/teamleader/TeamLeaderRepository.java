package com.equipo5.feelflowapp.repository.users.teamleader;

import com.equipo5.feelflowapp.domain.users.TeamLeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamLeaderRepository extends JpaRepository<TeamLeader, UUID> {
}
