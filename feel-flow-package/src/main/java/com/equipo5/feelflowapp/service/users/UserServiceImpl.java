package com.equipo5.feelflowapp.service.users;

import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.dto.users.UserUpdateDTO;
import com.equipo5.feelflowapp.mappers.users.UserMapper;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public Optional<UserDTO> updateUser(UUID uuidUser, UserUpdateDTO userUpdateDTO) {

        Optional<User> userDTO = userRepository.findById(uuidUser);

        if (userDTO.isPresent()){

            User user = userDTO.get();

            user.setName(userUpdateDTO.getName());
            user.setSurname(userUpdateDTO.getSurname());
            user.setUsername(userUpdateDTO.getUsername());


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
