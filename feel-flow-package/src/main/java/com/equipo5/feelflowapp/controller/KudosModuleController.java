package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.dto.modules.CreationKudosModuleDto;
import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.service.module.kudos.KudosService;
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
        name = "Module Kudos REST APIs",
        description = "REST APIs in Project to CREATE, UPDATE, GET AND DELETE Kudos Module"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = KudosModuleController.MODULE_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class KudosModuleController {

    public  static final String MODULE_PATH = "/api/v1/kudos";

    private final String MODULE = "Module";

    private final KudosService kudosService;

    private final NotificationService notificationService;


    @Operation(
            summary = "Create Module Kudos REST API",
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
            @RequestBody CreationKudosModuleDto creationKudosModuleDto
    ){
        KudosModule kudosModule = this.kudosService.publishingModule(creationKudosModuleDto);
        // Notificar a miembros
        notificationService.sendNotificationModule(
                kudosModule.getTeam().getRegularUsers(),
                notificationService.generateBodyForOpenedModule("Kudos", kudosModule.getDateAndTimeToPublish(), kudosModule.getDateAndTimeToClose()),
                "Apertura de modulo Kudos!"
        );
        notificationService.sendNotificationModule(
                kudosModule.getTeam().getTeamLeader(),
                notificationService.generateBodyForOpenedModule("Kudos", kudosModule.getDateAndTimeToPublish(), kudosModule.getDateAndTimeToClose()),
                "Apertura de modulo Kudos!"
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpResponses.STATUS_201,String.format(HttpResponses.MESSAGE_201,MODULE)));
    }

    @Operation(
            summary = "Create Module Kudos REST API",
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
    @GetMapping("/available_module")
    public boolean isModuleKudosAvailable() {
        return this.kudosService.isModuleKudosAvailable();
    }


}
