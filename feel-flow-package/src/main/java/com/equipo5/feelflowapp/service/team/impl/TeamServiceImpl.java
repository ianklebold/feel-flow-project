package com.equipo5.feelflowapp.service.team.impl;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import com.equipo5.feelflowapp.dto.team.TeamListDTO;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamLeaderException;
import com.equipo5.feelflowapp.mappers.users.team.TeamListMapper;
import com.equipo5.feelflowapp.mappers.users.team.TeamMapper;
import com.equipo5.feelflowapp.mappers.users.teamleader.TeamLeaderMapper;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.admin.AdminRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.repository.users.teamleader.TeamLeaderRepository;
import com.equipo5.feelflowapp.service.enterprise.EnterpriseService;
import com.equipo5.feelflowapp.service.team.TeamService;
import com.equipo5.feelflowapp.service.users.UserService;
import com.equipo5.feelflowapp.service.users.teamleader.TeamLeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final TeamLeaderRepository teamLeaderRepository;

    private final RegularUserRepository regularUserRepository;

    private final AdminRepository adminRepository;
    private final TeamMapper teamMapper;

    private final TeamLeaderService teamLeaderService;

    private final TeamLeaderMapper teamLeaderMapper;

    private final EnterpriseService enterpriseService;

    private final UserService userService;

    private final TeamListMapper teamListMapper;
    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team teamToCreate = teamMapper.teamDtoToTeam(teamDTO);

        setEnterprise(teamToCreate);
        setTeamLeader(teamToCreate,teamDTO);
        teamRepository.save(teamToCreate);
        teamToCreate.getTeamLeader().setPassword(teamDTO.getTeamLeaderDTO().getPassword());
        return teamMapper.teamToTeamDto(teamToCreate);
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

    @Override
    public List<TeamListDTO> getAllTeams() {
        Optional<? extends GrantedAuthority> role = userService.getRoleByCurrentUser();
        String teamId = "";
        if (role.isPresent()){
            String username = userService.getUsernameByCurrentUser();
            if (TeamRoles.ADMIN.name().equals(role.get().getAuthority())){

                Optional<Admin> admin = adminRepository.findByUsername(username);

                return admin.map(value -> value.getEnterPrise().getTeam()
                        .stream()
                        .map(teamListMapper::teamToTeamListDto)
                        .toList()).orElse(Collections.emptyList());

            }else if (TeamRoles.TEAM_LEADER.name().equals(role.get().getAuthority())){
                teamId = teamLeaderRepository.findTeamByUsername(username);
            }else {
                teamId = regularUserRepository.findTeamByUsername(username);
            }
        }
        Optional<Team> team = teamRepository.findById(UUID.fromString(teamId));
        return team.map(value -> List.of(teamListMapper.teamToTeamListDto(value))).orElse(Collections.emptyList());
    }

    @Override
    public Optional<TeamListDTO> getTeamById(UUID uuid) throws NotFoundException {
        Optional<Team> team = teamRepository.findById(uuid);
        Optional<? extends GrantedAuthority> role = userService.getRoleByCurrentUser();
        if (team.isEmpty()){
            throw new NotFoundTeamException("No se encuentra equipo con UUID : " + uuid);
        }

        if (role.isPresent()){
            if (TeamRoles.TEAM_LEADER.name().equals(role.get().getAuthority()) || TeamRoles.USER_REGULAR.name().equals(role.get().getAuthority())){
                List<TeamListDTO> teams = getAllTeams();
                //Obtenemos la lista o el equipo del current user, preguntamos si el equipo que se desea acceder el current user lo integra
                return teams.stream().filter(teamOfList -> team.get().getUuid().equals(teamOfList.getUuid())).findFirst();
            }
            return Optional.of(teamListMapper.teamToTeamListDto(team.get()));
        }

        return Optional.empty();
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
