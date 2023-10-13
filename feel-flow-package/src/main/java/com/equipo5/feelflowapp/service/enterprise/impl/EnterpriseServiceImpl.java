package com.equipo5.feelflowapp.service.enterprise.impl;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.repository.enterprise.EnterpriseRepository;
import com.equipo5.feelflowapp.repository.users.admin.AdminRepository;
import com.equipo5.feelflowapp.service.enterprise.EnterpriseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final AdminRepository adminRepository;

    @Override
    public EnterPrise createEnterprise(EnterPrise enterPrise) {

        return enterpriseRepository.save(enterPrise);
    }

    @Override
    public Optional<EnterPrise> getEnterpriseByCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Admin> admin = adminRepository.findByUsername(username);

        return admin.map(Admin::getEnterPrise);
    }
}
