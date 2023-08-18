package com.equipo5.feelflowapp.mappers.users.admin;

import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.dto.AdminDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AdminMapper {
    Admin adminDtoToAdmin(AdminDTO adminDTO);
    AdminDTO adminToAdminDto(Admin admin);
}
