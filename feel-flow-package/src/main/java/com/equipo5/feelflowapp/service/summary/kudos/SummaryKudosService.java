package com.equipo5.feelflowapp.service.summary.kudos;

import com.equipo5.feelflowapp.dto.badges.BadgeTeamDto;

import java.util.List;
import java.util.UUID;

public interface SummaryKudosService {
    List<BadgeTeamDto> getSummary(Long idModule,UUID idRegularUser);
}
