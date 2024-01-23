package com.equipo5.feelflowapp.service.users.regularuser;

import com.equipo5.feelflowapp.dto.users.RegularUserDTO;
import com.equipo5.feelflowapp.exception.badrequest.invitation.InvitationApprovedException;
import com.equipo5.feelflowapp.exception.badrequest.invitation.InvitationExpiredException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;

import java.util.UUID;

public interface RegularUserService {
    RegularUserDTO createRegularUser(RegularUserDTO regularUserDTO, UUID uuidInvitation) throws InvitationExpiredException, NotFoundException, InvitationApprovedException;
}
