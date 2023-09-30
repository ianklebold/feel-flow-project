package com.equipo5.feelflowapp.mappers.users.teamleader;

import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TeamLeaderMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    TeamLeader teamLeaderDtoToTeamLeader(TeamLeaderDTO teamLeaderDTO);

    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    TeamLeaderDTO teamLeaderToTeamLeaderDto(TeamLeader teamLeader);
}
