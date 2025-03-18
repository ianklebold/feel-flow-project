package com.equipo5.feelflowapp.mappers.users;

import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.dto.users.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO userToUserDto(User user);

    User userDtoToUser(UserDTO userDTO);
}
