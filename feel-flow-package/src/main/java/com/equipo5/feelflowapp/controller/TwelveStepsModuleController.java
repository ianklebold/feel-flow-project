package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.service.module.twelveSteps.TwelveStepsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/twelve_steps_modules",produces = {MediaType.APPLICATION_JSON_VALUE})
public class TwelveStepsModuleController {

    public  static final String MODULE_PATH = "/api/v1/twelve_steps_modules";
    public static final String PATH_TEAM_ID = "/{idTeam}";
    private final String MODULE = "Module";
    private final TwelveStepsService twelveStepsService;

    @PostMapping(PATH_TEAM_ID)
    public ResponseEntity<ResponseDto> publishingModule(@PathVariable(value = "idTeam") UUID idTeam){

        twelveStepsService.publishingModule(idTeam);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpResponses.STATUS_201,String.format(HttpResponses.MESSAGE_201,MODULE)));
    }
}
