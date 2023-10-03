package com.equipo5.feelflowapp.service.users.invitation;

import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.users.invitation.InvitationTeamDTO;
import com.equipo5.feelflowapp.exception.expiration.InvitationApprovedException;
import com.equipo5.feelflowapp.exception.expiration.InvitationExpiredException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;

import java.util.UUID;

public interface InvitationService {
    InvitationTeamDTO createInvitation(UUID idTeam) throws NotFoundException;


    InvitationTeamDTO approveInvitation(RegularUser regularUser, UUID uuidInvitation) throws InvitationExpiredException, NotFoundException, InvitationApprovedException;
}
