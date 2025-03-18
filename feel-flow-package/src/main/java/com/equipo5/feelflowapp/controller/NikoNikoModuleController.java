package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import com.equipo5.feelflowapp.dto.modules.CreationNikoNikoModule;
import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.service.module.nikoniko.NikoNikoService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Module Niko Niko REST APIs",
        description = "REST APIs in Project to CREATE, UPDATE, GET AND DELETE NIKO NIKO Module"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = NikoNikoModuleController.MODULE_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class NikoNikoModuleController {
    public  static final String MODULE_PATH = "/api/v1/niko_niko";

    private final String MODULE = "Module";

    private final NikoNikoService nikoNikoService;

    private final NotificationService notificationService;

    @Operation(
            summary = "Create Module Niko Niko REST API",
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
            @RequestBody CreationNikoNikoModule creationNikoNikoModule
            ){

        NikoNikoModule nikoNikoModule = this.nikoNikoService.publishingModule(creationNikoNikoModule);
        notificationService.sendNotificationModule(
                nikoNikoModule.getTeam().getRegularUsers(),
                notificationService.generateBodyForOpenedModule("Niko Niko", nikoNikoModule.getDateAndTimeToPublish(), nikoNikoModule.getDateAndTimeToClose()),
                "Apertura de nuevo modulo"
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpResponses.STATUS_201,String.format(HttpResponses.MESSAGE_201,MODULE)));
    }

}
