package com.equipo5.feelflowapp.service.badges;

import com.equipo5.feelflowapp.dto.badges.*;

import java.util.List;
import java.util.UUID;

public interface BadgesService {
    void sendBadge(BadgesAwardedDto badgesAwardedDto);

    List<BadgesAvailableDto> getBadgesAvailableToSend();

    List<BadgeWithNumberOfBadgesDto> getBadgesAwarded();
    List<BadgeWithNumberOfBadgesDto> getBadgesAwarded(UUID userId);

    List<BadgeTeamDto> getBadgeTeams();
}
