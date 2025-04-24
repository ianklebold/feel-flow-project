package com.equipo5.feelflowapp.mappers.badges.custom.impl;

import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.modules.kudos.TableBadge;
import com.equipo5.feelflowapp.dto.badges.BadgesAvailableDto;
import com.equipo5.feelflowapp.mappers.badges.custom.BadgeAvailableMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BadgeAvailableMapperImpl implements BadgeAvailableMapper {

    @Override
    public List<BadgesAvailableDto> badgeToBadgeAvailableDto(TableBadge tableBadge,List<BadgesAvailableDto> badgesAvailableDtos) {

        if(tableBadge == null) {
            return badgesAvailableDtos;
        }else {
            BadgesAvailableDto badgesAvailableDto = this.badgeToBadgeAvailableDto(tableBadge.getMasterOfDetail(), BadgeName.MAESTRO_DEL_DETALLE);
            if(badgesAvailableDto != null) {
                badgesAvailableDtos.add(badgesAvailableDto);
            }
            BadgesAvailableDto badgesAvailableDto2 = this.badgeToBadgeAvailableDto(tableBadge.getBadgePositiveEnergy(), BadgeName.ENERGIA_POSITIVA);
            if(badgesAvailableDto2 != null) {
                badgesAvailableDtos.add(badgesAvailableDto2);
            }
            BadgesAvailableDto badgesAvailableDto3 = this.badgeToBadgeAvailableDto(tableBadge.getBadgeResolutorStar(), BadgeName.RESOLUTOR_ESTRELLA);
            if(badgesAvailableDto3 != null) {
                badgesAvailableDtos.add(badgesAvailableDto3);
            }
        }
        
        
        return badgesAvailableDtos;
    }

    @Override
    public List<BadgesAvailableDto> badgeToBadgeAvailableDto(TableBadge tableBadge, int teamNumber,List<BadgesAvailableDto> badgesAvailableDtos) {
        BadgesAvailableDto badgesAvailableDto = this.badgeToBadgeAvailableDto(tableBadge.getBadgeFriendHands(),teamNumber, BadgeName.MANOS_AMIGAS);
        if(badgesAvailableDto != null) {
            badgesAvailableDtos.add(badgesAvailableDto);
        }
        return badgesAvailableDtos;
    }

    private BadgesAvailableDto badgeToBadgeAvailableDto(Badge badge, BadgeName badgeName) {
        if(badge == null) {
            return new BadgesAvailableDto(badgeName, 1);
        }else{
            return new BadgesAvailableDto(badgeName, 0);
        }
    }

    private BadgesAvailableDto badgeToBadgeAvailableDto(List<Badge> badge, int teamNumber, BadgeName badgeName) {
        int numberOfBadges = (teamNumber - 1) - badge.size() ;

        if(numberOfBadges > 0) {
            return new BadgesAvailableDto(badgeName, numberOfBadges);
        }else{
            return new BadgesAvailableDto(badgeName, 0);
        }

    }


}
