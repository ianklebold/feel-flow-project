package com.equipo5.feelflowapp.service.badges;

import com.equipo5.feelflowapp.dto.badges.*;

import java.util.List;

public interface BadgesService {
    void sendBadge(BadgesAwardedDto badgesAwardedDto);

    List<BadgesAvailableDto> getBadgesAvailableToSend();

    List<BadgeWithNumberOfBadgesDto> getBadgesAwarded();

    List<BadgeTeamDto> getBadgeTeams();
}
