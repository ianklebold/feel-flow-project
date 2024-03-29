package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.dto.invitation.InvitationTeamDTO;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import com.equipo5.feelflowapp.dto.team.TeamListDTO;
import com.equipo5.feelflowapp.dto.team.TeamUpdateDTO;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.service.team.TeamService;
import com.equipo5.feelflowapp.service.users.invitation.InvitationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping()
    @SecurityRequirement(name = "Bearer Authentication")
    public List<TeamListDTO> getAllTeams(){

        return teamService.getAllTeams();
    }

    @GetMapping(PATH_ID)
    public ResponseEntity getAllTeams(@PathVariable(value = "idTeam") UUID idTeam){

        try {
            Optional<TeamListDTO> teamListDTO = teamService.getTeamById(idTeam);

            if (teamListDTO.isPresent()){
                return new ResponseEntity(teamListDTO.get(),HttpStatus.OK);
            }else {
                return new ResponseEntity("Usuario no integra el equipo o no existe equipo",HttpStatus.FORBIDDEN);
            }

        } catch (NotFoundException e) {
            throw new RuntimeException("No existe equipo");
        }

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

    @PutMapping(PATH_ID)
    public ResponseEntity updateTeam(@PathVariable(value = "idTeam") UUID idTeam,@Validated @RequestBody TeamUpdateDTO teamDTO){
        try {
            Optional<TeamListDTO> teamListDTO = teamService.updateTeam(idTeam,teamDTO);

            if (teamListDTO.isPresent()){
                return new ResponseEntity(teamListDTO.get(),HttpStatus.OK);
            }else {
                return new ResponseEntity("Usuario no integra el equipo o no existe equipo",HttpStatus.FORBIDDEN);
            }

        } catch (NotFoundException e) {
            throw new RuntimeException("No existe equipo");
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
