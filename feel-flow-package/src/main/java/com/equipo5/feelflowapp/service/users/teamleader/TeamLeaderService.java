package com.equipo5.feelflowapp.service.users.teamleader;

import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;

import java.util.Optional;
import java.util.UUID;

public interface TeamLeaderService {
    TeamLeader createTeamLeader(TeamLeaderDTO teamLeaderDTO);
}
