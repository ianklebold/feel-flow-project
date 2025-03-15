package com.equipo5.feelflowapp.mappers.users;

import com.equipo5.feelflowapp.domain.images.MediaImage;
import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.dto.users.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(uses = {MediaImage.class})
public interface UserMapper {

    @Mapping(source = "mediaImage", target = "mediaImage" , qualifiedByName = "convertBase64ToBytes")
    UserDTO userToUserDto(User user);

    @Mapping(source = "mediaImage", target = "mediaImage", qualifiedByName = "convertBytesToBase64")
    User userDtoToUser(UserDTO userDTO);
}
