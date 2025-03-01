package com.equipo5.feelflowapp.mappers.badges.custom;

import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.dto.badges.BadgeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BadgesDtoMapper {
    @Mapping(source = "badgeName",target = "badgeName")
    @Mapping(source = "awardedDate",target = "awardedDate")
    BadgeDto badgeToBadgeDto(Badge badge);
}
