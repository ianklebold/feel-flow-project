package com.equipo5.feelflowapp.repository.users.invitationteam;


import com.equipo5.feelflowapp.domain.users.InvitationTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvitationTeamRepository extends JpaRepository<InvitationTeam, UUID> {
}
