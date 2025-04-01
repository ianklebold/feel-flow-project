package com.equipo5.feelflowapp.mappers.modules.kudos;

import com.equipo5.feelflowapp.dto.dashboard.kudos.KudosSummaryData;
import com.equipo5.feelflowapp.dto.tablebadge.CountBadgeAwardedDto;
import com.equipo5.feelflowapp.dto.tablebadge.TableBadgeAwardedDto;
import com.equipo5.feelflowapp.service.utils.dateservice.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;

@Mapper
public interface KudosSummaryDataMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "highlight", target = "manosAmigasBadge", qualifiedByName = "setHighLightDependsCountOfManosAmigasBadge")
    KudosSummaryData tableBadgeAwardedDtoToKudosSummaryData(TableBadgeAwardedDto tableBadgeAwardedDto);

    @Named("setHighLightDependsCountOfManosAmigasBadge")
    public static boolean setHighLightDependsCountOfManosAmigasBadge(CountBadgeAwardedDto manosAmigasBadge){
        if(manosAmigasBadge == null){
            return false;
        }else{
            return manosAmigasBadge.getCountAwarded() > 0;
        }
    }

}
