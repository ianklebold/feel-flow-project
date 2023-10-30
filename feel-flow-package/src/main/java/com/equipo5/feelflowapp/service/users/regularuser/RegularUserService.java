package com.equipo5.feelflowapp.service.users.regularuser;

import com.equipo5.feelflowapp.dto.users.RegularUserDTO;
import com.equipo5.feelflowapp.exception.expiration.InvitationApprovedException;
import com.equipo5.feelflowapp.exception.expiration.InvitationExpiredException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;

import java.util.UUID;


public interface RegularUserService {
    RegularUserDTO createRegularUser(RegularUserDTO regularUserDTO, UUID uuidInvitation) throws InvitationExpiredException, NotFoundException, InvitationApprovedException;
}
