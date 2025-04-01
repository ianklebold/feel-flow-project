package com.equipo5.feelflowapp.mappers.tablebadge;

import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.modules.kudos.TableBadge;
import com.equipo5.feelflowapp.dto.tablebadge.TableBadgeAwardedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(uses = BadgeAwardedMapper.class)
public interface TableBadgeAwardedMapper {

    @Mapping(source = "badgeFriendHands", target = "manosAmigasBadge", qualifiedByName = "manosAmigasBadgeMapper")
    @Mapping(source = "badgeResolutorStar", target = "resolutorEstrellaBadge")
    @Mapping(source = "badgePositiveEnergy", target = "energiaPositivaBadge")
    @Mapping(source = "masterOfDetail", target = "maestroDetalleBadge")
    TableBadgeAwardedDto tableBadgeAwardedToTableBadgeAwardedDto(TableBadge tableBadge);

    @Named("manosAmigasBadgeMapper")
    public static Badge manosAmigasBadgeMapper(List<Badge> badgeFriendHands) {

        if (!badgeFriendHands.isEmpty()){
            return badgeFriendHands.get(0);
        }
        return null;
    }

}
