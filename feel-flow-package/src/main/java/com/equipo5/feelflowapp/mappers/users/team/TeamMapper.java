package com.equipo5.feelflowapp.mappers.users.team;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TeamMapper {


    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "nameTeam", target = "name")
    @Mapping(source = "descriptionTeam", target = "descriptionProject")
    Team teamDtoToTeam(TeamDTO teamDTO);

    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "name", target = "nameTeam")
    @Mapping(source = "descriptionProject", target = "descriptionTeam")
    @Mapping(source = "teamLeader", target = "teamLeaderDTO")
    TeamDTO teamToTeamDto(Team team);
}
