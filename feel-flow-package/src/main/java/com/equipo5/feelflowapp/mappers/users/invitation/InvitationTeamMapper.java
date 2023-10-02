package com.equipo5.feelflowapp.mappers.users.invitation;

import com.equipo5.feelflowapp.domain.users.InvitationTeam;
import com.equipo5.feelflowapp.dto.users.invitation.InvitationTeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface InvitationTeamMapper {

    @Mapping(source = "team",target = "teamDTO")
    @Mapping(source = "regularUser",target = "regularUserDTO")
    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "expirationDate", target = "isExpirated")
    InvitationTeamDTO invitationTeamToInvitationTeamDTO(InvitationTeam invitationTeam);
}
