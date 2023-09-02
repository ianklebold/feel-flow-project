package com.equipo5.feelflowapp.mappers.users.admin;

import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.dto.users.admin.AdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AdminMapper {



    @Mapping(source = "enterpriseDTO", target = "enterPrise")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    Admin adminDtoToAdmin(AdminDTO adminDTO);

    @Mapping(source = "enterPrise", target = "enterpriseDTO")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    AdminDTO adminToAdminDto(Admin admin);
}
