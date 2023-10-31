package com.equipo5.feelflowapp.service.users.invitation.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.users.InvitationTeam;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.invitation.InvitationTeamDTO;
import com.equipo5.feelflowapp.exception.expiration.InvitationApprovedException;
import com.equipo5.feelflowapp.exception.expiration.InvitationExpiredException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.mappers.users.invitation.InvitationTeamMapper;
import com.equipo5.feelflowapp.mappers.users.team.TeamMapper;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.invitationteam.InvitationTeamRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.users.invitation.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final TeamRepository teamRepository;
    private final InvitationTeamMapper invitationTeamMapper;

    private final TeamMapper teamMapper;
    private final InvitationTeamRepository invitationTeamRepository;

    private final RegularUserRepository regularUserRepository;
    public static final int DAYS_OF_EXPIRATION = 2;
    @Override
    public InvitationTeamDTO createInvitation(UUID idTeam) throws NotFoundException {
        Optional<Team> team = teamRepository.findById(idTeam);

        if (team.isPresent()){
            InvitationTeam invitationTeam = new InvitationTeam();
            invitationTeam.setTeam(team.get());
            invitationTeam.setExpirationDate(getDateOfExpiration());
            invitationTeam.setApproved(Boolean.FALSE);

            InvitationTeamDTO invitationTeamDTO = invitationTeamMapper.invitationTeamToInvitationTeamDTO( invitationTeamRepository.save(invitationTeam)  );
            invitationTeamDTO.setTeamDTO(teamMapper.teamToTeamDto(team.get()));

            return invitationTeamDTO;
        }else {
            throw new NotFoundException("Team not found");
        }
    }

    @Override
    public InvitationTeamDTO approveInvitation(RegularUser regularUser, UUID uuidInvitation) throws InvitationExpiredException, NotFoundException, InvitationApprovedException {

        Optional<InvitationTeam> invitationTeam = invitationTeamRepository.findById(uuidInvitation);

        if (invitationTeam.isPresent() && !invitationTeam.get().isApproved() && !invitationTeam.get().getExpirationDate()){
            invitationTeam.get().setApproved(Boolean.TRUE);
            invitationTeam.get().setRegularUser(regularUser);
            invitationTeam.get().setDateOfApproval(LocalDateTime.now());

            regularUser.setTeam(invitationTeam.get().getTeam());
            regularUserRepository.save(regularUser);

            InvitationTeamDTO invitationTeamDTO = invitationTeamMapper.invitationTeamToInvitationTeamDTO( invitationTeamRepository.save(invitationTeam.get()) );
            invitationTeamDTO.setTeamDTO(teamMapper.teamToTeamDto(invitationTeam.get().getTeam()));
            return invitationTeamDTO;
        } else if (invitationTeam.isEmpty()) {
            throw new NotFoundException("Invitation Not found");
        } else if (invitationTeam.get().isApproved()) {
            throw new InvitationApprovedException("Esta invitacion ya fue aprobada");
        }
        else {
            throw new InvitationExpiredException("Invitation expired");
        }
    }

    private Date getDateOfExpiration(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, DAYS_OF_EXPIRATION);
        return calendar.getTime();
    }
}
