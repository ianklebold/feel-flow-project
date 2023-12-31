package com.equipo5.feelflowapp.service.users;

import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.dto.users.UserUpdateDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<UserDTO> updateUser(UUID uuidUser, UserUpdateDTO userUpdateDTO);

    Optional<UserDTO> getUserById(UUID uuid);

    Optional<? extends GrantedAuthority> getRoleByCurrentUser();

    String getUsernameByCurrentUser();
}
