package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import com.equipo5.feelflowapp.dto.modules.CreationTwelveStepsModuleDto;
import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.service.module.twelveSteps.TwelveStepsService;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Module Twelve Steps REST APIs",
        description = "REST APIs in Project to CREATE, UPDATE, GET AND DELETE Twelve Steps Module"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = TwelveStepsModuleController.MODULE_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class TwelveStepsModuleController {

    public  static final String MODULE_PATH = "/api/v1/twelve_steps_modules";
    public static final String PATH_TEAM_ID = "/{idTeam}";
    private final String MODULE = "Module";
    private final TwelveStepsService twelveStepsService;
    private final NotificationService notificationService;


    @Operation(
            summary = "Create Module Twelve Steps REST API",
            description = "REST API to create/open new module"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status BAD REQUEST",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping()
    public ResponseEntity<ResponseDto> publishingModule(
            @RequestBody CreationTwelveStepsModuleDto creationTwelveStepsModuleDto
            ){

        TwelveStepsModule twelveStepsModule = twelveStepsService.publishingModule(creationTwelveStepsModuleDto);
        notificationService.sendNotificationModule(
                twelveStepsModule.getTeam().getRegularUsers(),
                notificationService.generateBodyForOpenedModule("12 pasos de la felicidad", twelveStepsModule.getDateAndTimeToPublish(), twelveStepsModule.getDateAndTimeToClose()),
                "Apertura de nuevo modulo"
        );
        notificationService.sendNotificationModule(
                twelveStepsModule.getTeam().getTeamLeader(),
                notificationService.generateBodyForOpenedModule("12 pasos de la felicidad", twelveStepsModule.getDateAndTimeToPublish(), twelveStepsModule.getDateAndTimeToClose()),
                "Apertura de nuevo modulo"
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpResponses.STATUS_201,String.format(HttpResponses.MESSAGE_201,MODULE)));
    }

}
