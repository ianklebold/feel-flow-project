package com.equipo5.feelflowapp.mappers.tablebadge;

import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.dto.tablebadge.CountBadgeAwardedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface BadgeAwardedMapper {

    @Mapping(source = "badgeName", target = "badgeName", qualifiedByName = "badgeNameMapper")
    CountBadgeAwardedDto badgeToCountBadgeAwardedDto(Badge badge);


    @Named("badgeNameMapper")
    public static String badgeNameMapper(BadgeName badgeName) {
        return badgeName.toString();
    }

}
