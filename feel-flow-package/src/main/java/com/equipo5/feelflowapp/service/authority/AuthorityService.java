package com.equipo5.feelflowapp.service.authority;

import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.users.Authority;

import java.util.Optional;

public interface AuthorityService {
    Optional<Authority> findAuthorityByTeamRoles(TeamRoles teamRoles);
}
