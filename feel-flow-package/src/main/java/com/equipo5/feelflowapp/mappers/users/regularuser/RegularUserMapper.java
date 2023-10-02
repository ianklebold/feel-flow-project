package com.equipo5.feelflowapp.mappers.users.regularuser;

import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.users.RegularUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RegularUserMapper {

    @Mapping(source = "uuid",target = "uuid")
    @Mapping(source = "name",target = "name")
    @Mapping(source = "surname",target = "surname")
    @Mapping(source = "username",target = "username")
    RegularUserDTO regularUserToRegularUserDTO(RegularUser regularUser);
}
