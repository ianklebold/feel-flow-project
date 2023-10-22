package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.dto.users.UserUpdateDTO;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.service.users.UserService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    public  static final String USER_PATH = "/api/v1/user";

    public static final String PATH_ID = "/{idUser}";

    private final UserService userService;


    @PutMapping(PATH_ID)
    public ResponseEntity updateUser(@PathVariable(value = "idUser")UUID idUser, @Validated @RequestBody UserUpdateDTO userUpdateDTO, @RequestHeader (name="Authorization") String token, @AuthenticationPrincipal Jwts jwts) throws NotFoundException {
        Optional<UserDTO> userUpdated = userService.updateUser(idUser,userUpdateDTO);

        if (userUpdated.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(PATH_ID)
    public ResponseEntity getUser(@PathVariable(value = "idUser")UUID idUser){
        Optional<UserDTO> userUpdated = userService.getUserById(idUser);

        if (userUpdated.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(userUpdated.get(),HttpStatus.OK);
    }


}
