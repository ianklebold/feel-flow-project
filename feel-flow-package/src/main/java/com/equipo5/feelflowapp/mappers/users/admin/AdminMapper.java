package com.equipo5.feelflowapp.mappers.users.admin;

import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.dto.users.admin.AdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AdminMapper {

    @Mapping(source = "enterpriseDTO", target = "enterPrise")
    Admin adminDtoToAdmin(AdminDTO adminDTO);
    AdminDTO adminToAdminDto(Admin admin);
}
