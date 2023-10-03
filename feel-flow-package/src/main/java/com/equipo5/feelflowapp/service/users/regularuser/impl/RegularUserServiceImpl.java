package com.equipo5.feelflowapp.service.users.regularuser.impl;

import com.equipo5.feelflowapp.domain.users.InvitationTeam;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.users.RegularUserDTO;
import com.equipo5.feelflowapp.dto.users.invitation.InvitationTeamDTO;
import com.equipo5.feelflowapp.exception.expiration.InvitationApprovedException;
import com.equipo5.feelflowapp.exception.expiration.InvitationExpiredException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.mappers.users.regularuser.RegularUserMapper;
import com.equipo5.feelflowapp.mappers.users.team.TeamMapper;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.users.invitation.InvitationService;
import com.equipo5.feelflowapp.service.users.regularuser.RegularUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegularUserServiceImpl implements RegularUserService {

    private final InvitationService invitationService;

    private final RegularUserRepository regularUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final TeamMapper teamMapper;
    private final RegularUserMapper regularUserMapper;

    @Override
    public RegularUserDTO createRegularUser(RegularUserDTO regularUserDTO, UUID uuidInvitation) throws InvitationExpiredException, NotFoundException, InvitationApprovedException {

        //Crear el usuario
        RegularUser regularUser = regularUserMapper.regularUserDtoToRegularUser(regularUserDTO);
        regularUser.setPassword(passwordEncoder.encode(regularUserDTO.getPassword()));
        //Actualizar la invitacion con servicio de invitacion  RegularUser regularUser, UUID uuidInvitation
        InvitationTeamDTO invitationTeamDTO = invitationService.approveInvitation(regularUserRepository.save(regularUser),uuidInvitation);
        invitationTeamDTO.getRegularUserDTO().setTeamDTO(teamMapper.teamToTeamDto(regularUser.getTeam()));

        return invitationTeamDTO.getRegularUserDTO();
    }
}
