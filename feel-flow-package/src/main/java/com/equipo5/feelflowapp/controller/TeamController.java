package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.dto.team.TeamDTO;
import com.equipo5.feelflowapp.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {
    public  static final String TEAM_PATH = "/api/v1/team";

    private final TeamService teamService;

    @PostMapping()
    public ResponseEntity createTeam(@Validated @RequestBody TeamDTO teamDTO){
        TeamDTO teamCreated = teamService.createTeam(teamDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location",TEAM_PATH.concat("/").concat(teamCreated.getUuid().toString()));

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }
}
