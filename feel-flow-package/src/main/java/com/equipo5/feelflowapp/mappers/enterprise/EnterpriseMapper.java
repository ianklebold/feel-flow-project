package com.equipo5.feelflowapp.mappers.enterprise;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.dto.enterprise.EnterpriseDTO;
import org.mapstruct.Mapper;

@Mapper
public interface EnterpriseMapper {
    EnterPrise enterPriseDtoToEnterprise(EnterpriseDTO enterpriseDTO);
}
