package com.equipo5.feelflowapp.service.users.teamleader.impl;

import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.users.Authority;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;
import com.equipo5.feelflowapp.mappers.users.teamleader.TeamLeaderMapper;
import com.equipo5.feelflowapp.repository.users.teamleader.TeamLeaderRepository;
import com.equipo5.feelflowapp.service.authority.AuthorityService;
import com.equipo5.feelflowapp.service.users.teamleader.TeamLeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamLeaderServiceImpl implements TeamLeaderService {
    private final TeamLeaderRepository teamLeaderRepository;
    private final TeamLeaderMapper teamLeaderMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityService authorityService;

    @Override
    public TeamLeader createTeamLeader(TeamLeaderDTO teamLeaderDTO) {
        TeamLeader teamLeader = teamLeaderMapper.teamLeaderDtoToTeamLeader(teamLeaderDTO);
        teamLeader.setPassword(passwordEncoder.encode(teamLeaderDTO.getPassword()));

        Optional<Authority> authority = authorityService.findAuthorityByTeamRoles(TeamRoles.TEAM_LEADER);
        authority.ifPresent(value -> teamLeader.setAuthorities(List.of(value)));

        return teamLeaderRepository.save(teamLeader);
    }
}
