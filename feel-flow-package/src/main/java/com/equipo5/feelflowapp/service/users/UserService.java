package com.equipo5.feelflowapp.service.users;

import com.equipo5.feelflowapp.dto.users.UserUpdateDTO;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<UserUpdateDTO> updateUser(UUID uuidUser, UserUpdateDTO userUpdateDTO);
}
