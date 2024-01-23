package com.equipo5.feelflowapp.controller;

import com.devskiller.friendly_id.FriendlyId;
import com.equipo5.feelflowapp.dto.users.RegularUserDTO;
import com.equipo5.feelflowapp.exception.badrequest.invitation.InvitationException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.service.users.regularuser.RegularUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/regular_user")
public class RegularUserController {
    public  static final String USER_PATH = "/api/v1/regular_user";

    public static final String PATH_INVITATION_ID = "/{invitation_id}";

    private final RegularUserService regularUserService;

    @PostMapping(PATH_INVITATION_ID)
    public ResponseEntity createRegularUser(@PathVariable(value = "invitation_id") String invitationId,
                                            @Validated @RequestBody RegularUserDTO regularUserDTO){

        try {
            RegularUserDTO regularUserDTOCreated = regularUserService.createRegularUser(regularUserDTO, FriendlyId.toUuid(invitationId));
            return new ResponseEntity(regularUserDTOCreated, HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            return new ResponseEntity(notFoundException.getMessage(),HttpStatus.NOT_FOUND);
        } catch (InvitationException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
