package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.dto.users.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    public  static final String USER_PATH = "/api/v1/user";

    public static final String PATH_ID = "/{id}";

    @PutMapping(PATH_ID)
    public ResponseEntity updateUser(@PathVariable(value = "idUser")UUID idUser, @RequestBody UserUpdateDTO userUpdateDTO){



    }


}
