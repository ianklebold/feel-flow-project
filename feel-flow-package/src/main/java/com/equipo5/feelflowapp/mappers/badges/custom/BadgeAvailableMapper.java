package com.equipo5.feelflowapp.mappers.badges.custom;


import com.equipo5.feelflowapp.domain.modules.kudos.TableBadge;
import com.equipo5.feelflowapp.dto.badges.BadgesAvailableDto;

import java.util.List;

public interface BadgeAvailableMapper {

    List<BadgesAvailableDto> badgeToBadgeAvailableDto(TableBadge tableBadge,List<BadgesAvailableDto> badgesAvailableDtos);
    List<BadgesAvailableDto> badgeToBadgeAvailableDto(TableBadge tableBadge,int teamNumber,List<BadgesAvailableDto> badgesAvailableDtos);

}
