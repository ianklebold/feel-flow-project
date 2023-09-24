package com.equipo5.feelflowapp.mappers.users.teamleader;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TeamLeaderMapper {


    @Mapping(source = "nameTeam", target = "name")
    @Mapping(source = "descriptionTeam", target = "descriptionProject")
    Team teamDtoToTeam(TeamDTO teamDTO);
}
