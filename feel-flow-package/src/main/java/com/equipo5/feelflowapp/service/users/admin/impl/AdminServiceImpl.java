package com.equipo5.feelflowapp.service.users.admin.impl;

import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.dto.AdminDTO;
import com.equipo5.feelflowapp.mappers.users.admin.AdminMapper;
import com.equipo5.feelflowapp.repository.users.admin.AdminRepository;
import com.equipo5.feelflowapp.service.users.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final AdminMapper adminMapper;

    @Override
    public Admin createAdmin(AdminDTO adminDTO) {

        Admin admin = adminMapper.adminDtoToAdmin(adminDTO);

        admin.setUuid(UUID.randomUUID());



        admin.setAuthorities(List.of());

        return adminRepository.save(admin);
    }
}
