package com.equipo5.feelflowapp.service.users.admin.impl;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.domain.users.Authority;
import com.equipo5.feelflowapp.dto.users.admin.AdminDTO;
import com.equipo5.feelflowapp.mappers.enterprise.custom.CustomEnterpriseMapper;
import com.equipo5.feelflowapp.mappers.users.admin.AdminMapper;
import com.equipo5.feelflowapp.repository.users.admin.AdminRepository;
import com.equipo5.feelflowapp.service.authority.AuthorityService;
import com.equipo5.feelflowapp.service.users.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AuthorityService authorityService;
    private final AdminMapper adminMapper;

    private final CustomEnterpriseMapper customEnterpriseMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {

        Admin admin = adminMapper.adminDtoToAdmin(adminDTO);

        admin.setUuid(UUID.randomUUID());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));

        Optional<Authority> authority = authorityService.findAuthorityByTeamRoles(TeamRoles.ADMIN);
        authority.ifPresent(value -> admin.setAuthorities(List.of(value)));
        admin.setAuthorities(List.of());

        EnterPrise enterPrise = customEnterpriseMapper.getEnterpriseFromAdmin(adminDTO);

        enterPrise.setTeam(Collections.emptyList());
        enterPrise.setAdmin(admin);

        admin.setEnterPrise(enterPrise);

        Admin adminCreated =  adminRepository.save(admin);

        return adminMapper.adminToAdminDto(adminCreated);
    }
}
