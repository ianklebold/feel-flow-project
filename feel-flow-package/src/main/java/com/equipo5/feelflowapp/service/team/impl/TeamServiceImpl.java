package com.equipo5.feelflowapp.service.team.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import com.equipo5.feelflowapp.mappers.users.team.TeamMapper;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.team.TeamService;
import com.equipo5.feelflowapp.service.users.teamleader.TeamLeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    private final TeamLeaderService teamLeaderService;
    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team teamToCreate = teamMapper.teamDtoToTeam(teamDTO);

        setTeamLeader(teamToCreate,teamDTO);

        return teamMapper.teamToTeamDto(teamRepository.save(teamToCreate));
    }

    private void setTeamLeader(Team teamToCreate,TeamDTO teamDTO){
        TeamLeader teamLeader =  teamLeaderService.createTeamLeader(teamDTO.getTeamLeaderDTO());
        teamToCreate.setTeamLeader(teamLeader);
    }
}
