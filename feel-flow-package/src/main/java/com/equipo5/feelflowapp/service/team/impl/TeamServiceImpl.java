package com.equipo5.feelflowapp.service.team.impl;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamLeaderException;
import com.equipo5.feelflowapp.mappers.users.team.TeamMapper;
import com.equipo5.feelflowapp.mappers.users.teamleader.TeamLeaderMapper;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.enterprise.EnterpriseService;
import com.equipo5.feelflowapp.service.team.TeamService;
import com.equipo5.feelflowapp.service.users.teamleader.TeamLeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    private final TeamLeaderService teamLeaderService;

    private final TeamLeaderMapper teamLeaderMapper;

    private final EnterpriseService enterpriseService;
    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team teamToCreate = teamMapper.teamDtoToTeam(teamDTO);

        setEnterprise(teamToCreate);
        setTeamLeader(teamToCreate,teamDTO);

        return teamMapper.teamToTeamDto(teamRepository.save(teamToCreate));
    }

    @Override
    public Optional<TeamLeaderDTO> teamLeaderByTeam(UUID uuid) throws NotFoundException {
        Optional<Team> team = teamRepository.findById(uuid);

        if (team.isEmpty()){
            throw new NotFoundException("Team not found");
        }

        if (Objects.nonNull(team.get().getTeamLeader())){
            return Optional.of(teamLeaderMapper.teamLeaderToTeamLeaderDto(team.get().getTeamLeader()));
        }else{
            throw new NotFoundTeamLeaderException("Not Exist Team Leader For Team");
        }
    }

    private void setTeamLeader(Team teamToCreate,TeamDTO teamDTO){
        TeamLeader teamLeader =  teamLeaderService.createTeamLeader(teamDTO.getTeamLeaderDTO());
        teamToCreate.setTeamLeader(teamLeader);
    }

    private void setEnterprise(Team teamToCreate){
        Optional<EnterPrise> enterPrise = enterpriseService.getEnterpriseByCurrentUser();
        enterPrise.ifPresent(teamToCreate::setEnterPrise);
    }
}
