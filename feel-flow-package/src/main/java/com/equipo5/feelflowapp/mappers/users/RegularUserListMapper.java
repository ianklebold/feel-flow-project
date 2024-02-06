package com.equipo5.feelflowapp.mappers.users;

import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.users.RegularUserListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RegularUserListMapper {

    @Mapping(source = "uuid",target = "uuid")
    @Mapping(source = "name",target = "name")
    @Mapping(source = "surname",target = "surname")
    @Mapping(source = "username",target = "username")
    RegularUserListDto regularUserToRegularUserListDto(RegularUser regularUser);
}
