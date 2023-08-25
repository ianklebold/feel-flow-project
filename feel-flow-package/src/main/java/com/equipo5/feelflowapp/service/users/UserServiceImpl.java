package com.equipo5.feelflowapp.service.users;

import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.dto.users.UserUpdateDTO;
import com.equipo5.feelflowapp.mappers.users.UserMapper;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    UserMapper userMapper;
    UserRepository userRepository;

    @Override
    public Optional<UserDTO> updateUser(UUID uuidUser, UserUpdateDTO userUpdateDTO) {

        Optional<UserDTO> userDTO = getUserById(uuidUser);

        if (userDTO.isPresent()){

            User user = userMapper.userDtoToUser(userDTO.get());

            user.setName(userDTO.get().getName());
            user.setSurname(userDTO.get().getSurname());
            user.setUsername(userDTO.get().getUsername());

            userRepository.save(user);

            return Optional.of(userMapper.userToUserDto(user));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> getUserById(UUID uuid) {
        return Optional.ofNullable(userMapper.userToUserDto( userRepository.findById(uuid).orElse(null) ));
    }


}
