package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.dto.users.UserUpdateDTO;
import com.equipo5.feelflowapp.exception.NotFoundException;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    public  static final String USER_PATH = "/api/v1/user";

    public static final String PATH_ID = "/{id}";

    private final UserService userService;

    @PutMapping(PATH_ID)
    public ResponseEntity updateUser(@PathVariable(value = "idUser")UUID idUser, @RequestBody UserUpdateDTO userUpdateDTO) throws NotFoundException {

        Optional<UserDTO> userUpdated = userService.updateUser(idUser,userUpdateDTO);

        if (userUpdated.isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
