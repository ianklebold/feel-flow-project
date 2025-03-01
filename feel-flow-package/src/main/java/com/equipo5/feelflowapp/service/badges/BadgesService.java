package com.equipo5.feelflowapp.service.badges;

import com.equipo5.feelflowapp.dto.badges.BadgeDto;
import com.equipo5.feelflowapp.dto.badges.BadgeTeamDto;
import com.equipo5.feelflowapp.dto.badges.BadgesAvailableDto;
import com.equipo5.feelflowapp.dto.badges.BadgesAwardedDto;

import java.util.List;

public interface BadgesService {
    void sendBadge(BadgesAwardedDto badgesAwardedDto);

    List<BadgesAvailableDto> getBadgesAvailableToSend();

    List<BadgeDto> getBadgesAwarded();

    List<BadgeTeamDto> getBadgeTeams();
}
