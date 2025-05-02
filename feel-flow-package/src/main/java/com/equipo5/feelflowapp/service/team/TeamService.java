package com.equipo5.feelflowapp.service.team;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import com.equipo5.feelflowapp.dto.team.TeamListDTO;
import com.equipo5.feelflowapp.dto.team.TeamUpdateDTO;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamService {
    TeamDTO createTeam(TeamDTO teamDTO);

    Optional<TeamLeaderDTO> teamLeaderByTeam(UUID uuid) throws NotFoundException;

    List<TeamListDTO> getAllTeams();

    List<Team> getAllTeamsEntities();

    List<TeamDTO> getTeamsByRole(boolean isAdmin);

    Optional<TeamListDTO> getTeamById(UUID uuid) throws NotFoundException;

    Optional<TeamListDTO> updateTeam(UUID uuid, TeamUpdateDTO teamDTO) throws NotFoundException;

    Optional<Team> getTeamByCurrentUser() throws NotFoundException;
}
