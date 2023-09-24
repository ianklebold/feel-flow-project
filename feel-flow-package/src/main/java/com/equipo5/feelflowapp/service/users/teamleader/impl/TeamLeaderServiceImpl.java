package com.equipo5.feelflowapp.service.users.teamleader.impl;

import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;
import com.equipo5.feelflowapp.mappers.users.teamleader.TeamLeaderMapper;
import com.equipo5.feelflowapp.repository.users.teamleader.TeamLeaderRepository;
import com.equipo5.feelflowapp.service.users.teamleader.TeamLeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamLeaderServiceImpl implements TeamLeaderService {
    private final TeamLeaderRepository teamLeaderRepository;
    private final TeamLeaderMapper teamLeaderMapper;

    @Override
    public TeamLeader createTeamLeader(TeamLeaderDTO teamLeaderDTO) {
        TeamLeader teamLeader = teamLeaderMapper.teamLeaderDtoToTeamLeader(teamLeaderDTO);

        return teamLeaderRepository.save(teamLeader);
    }
}
