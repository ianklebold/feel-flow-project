package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.dto.team.TeamDTO;
import com.equipo5.feelflowapp.dto.users.invitation.InvitationTeamDTO;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.service.users.invitation.InvitationService;
import com.equipo5.feelflowapp.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {
    public  static final String TEAM_PATH = "/api/v1/team";
    public static final String PATH_ID = "/{idTeam}";

    private final TeamService teamService;

    private final InvitationService invitationService;

    @PostMapping()
    public ResponseEntity createTeam(@Validated @RequestBody TeamDTO teamDTO){
        TeamDTO teamCreated = teamService.createTeam(teamDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location",TEAM_PATH.concat("/").concat(teamCreated.getUuid().toString()));

        return new ResponseEntity(teamCreated.getTeamLeaderDTO(),httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(PATH_ID+"/team-leader")
    public ResponseEntity teamLeaderByTeam(@PathVariable(value = "idTeam") UUID idTeam){
        try {
            Optional<TeamLeaderDTO> teamLeaderDTO = teamService.teamLeaderByTeam(idTeam);
            if (teamLeaderDTO.isPresent()){
                return new ResponseEntity(teamLeaderDTO,HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }catch (NotFoundException notFoundException){
            return new ResponseEntity(notFoundException.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(PATH_ID+"/invite")
    public ResponseEntity inviteToTeam(@PathVariable(value = "idTeam") UUID idTeam){
        try {
            //Invitacion
            InvitationTeamDTO invitationTeamDTO = invitationService.createInvitation(idTeam);
            return new ResponseEntity(invitationTeamDTO,HttpStatus.OK);

        }catch (NotFoundException notFoundException){
            return new ResponseEntity(notFoundException.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
