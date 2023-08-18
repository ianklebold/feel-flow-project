package com.equipo5.feelflowapp.mappers.enterprise.custom;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.dto.AdminDTO;

public interface CustomEnterpriseMapper {
    EnterPrise getEnterpriseFromAdmin(AdminDTO admin);
}
