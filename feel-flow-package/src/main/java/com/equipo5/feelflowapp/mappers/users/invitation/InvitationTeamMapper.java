package com.equipo5.feelflowapp.mappers.users.invitation;

import com.equipo5.feelflowapp.domain.users.InvitationTeam;
import com.equipo5.feelflowapp.dto.invitation.InvitationTeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface InvitationTeamMapper {
    @Mapping(source = "team",target = "teamDTO")
    @Mapping(source = "regularUser",target = "regularUserDTO")
    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "expirationDate", target = "isExpirated")
    @Mapping(source = "approved", target = "isApproved")
    InvitationTeamDTO invitationTeamToInvitationTeamDTO(InvitationTeam invitationTeam);

    InvitationTeam invitationTeamDtoToInvitationTeam(InvitationTeamDTO invitationTeamDTO);
}
