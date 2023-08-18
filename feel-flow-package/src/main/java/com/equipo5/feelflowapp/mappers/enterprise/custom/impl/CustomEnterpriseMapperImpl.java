package com.equipo5.feelflowapp.mappers.enterprise.custom.impl;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.dto.users.admin.AdminDTO;
import com.equipo5.feelflowapp.mappers.enterprise.custom.CustomEnterpriseMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomEnterpriseMapperImpl implements CustomEnterpriseMapper {
    @Override
    public EnterPrise getEnterpriseFromAdmin(AdminDTO admin) {

        EnterPrise.builder()
                .uuid(UUID.randomUUID())
                .name(admin.getEnterpriseDTO().getName())
                .build();

        return null;
    }
}
