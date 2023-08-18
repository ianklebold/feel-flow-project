package com.equipo5.feelflowapp.service.authority.impl;

import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.users.Authority;
import com.equipo5.feelflowapp.repository.users.AuthorityRepository;
import com.equipo5.feelflowapp.service.authority.AuthorityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public Optional<Authority> findAuthorityByTeamRoles(TeamRoles teamRoles) {
        return authorityRepository.findAuthorityByTeamRoles(teamRoles);
    }
}
