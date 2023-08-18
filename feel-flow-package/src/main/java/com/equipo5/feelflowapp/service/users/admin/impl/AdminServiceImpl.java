package com.equipo5.feelflowapp.service.users.admin.impl;

import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.domain.users.Authority;
import com.equipo5.feelflowapp.dto.AdminDTO;
import com.equipo5.feelflowapp.mappers.users.admin.AdminMapper;
import com.equipo5.feelflowapp.repository.users.AuthorityRepository;
import com.equipo5.feelflowapp.repository.users.admin.AdminRepository;
import com.equipo5.feelflowapp.service.users.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AuthorityRepository authorityRepository;

    private final AdminMapper adminMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Admin createAdmin(AdminDTO adminDTO) {

        Admin admin = adminMapper.adminDtoToAdmin(adminDTO);

        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        admin.setUuid(UUID.randomUUID());

        Optional<Authority> authority = authorityRepository.findAuthorityByTeamRoles(TeamRoles.ADMIN);
        authority.ifPresent(value -> admin.setAuthorities(List.of(value)));


        admin.setAuthorities(List.of());

        return adminRepository.save(admin);
    }
}
