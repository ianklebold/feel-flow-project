package com.equipo5.feelflowapp.mappers.users.team;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.dto.team.TeamListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TeamListMapper {



    @Mapping(source = "uuid",target = "uuid")
    @Mapping(source = "name",target = "nameTeam")
    @Mapping(source = "descriptionProject",target = "descriptionTeam")
    @Mapping(source = "teamLeader",target = "teamLeaderDTO")
    @Mapping(source = "regularUsers",target = "regularUsers")
    TeamListDTO teamToTeamListDto(Team team);
}
