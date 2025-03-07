package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.dto.badges.BadgesAvailableDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationClientDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationSessionUserDto;
import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name = "Notification REST APIs",
        description = "REST APIs in Project to CREATE Notifications"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = NotificationController.NOTIFICATION_PATH ,produces = {MediaType.APPLICATION_JSON_VALUE})
public class NotificationController {
    public  static final String NOTIFICATION_PATH = "/api/v1/notifications";

    private final NotificationService notificationService;

    @Operation(
            summary = "Create Module Niko Niko REST API",
            description = "REST API to create/open new module"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            )
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping()
    public ResponseEntity<ResponseDto> createNotification(@Valid @RequestBody NotificationClientDto notificationDto) {
        this.notificationService.sendNotification( notificationDto );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpResponses.STATUS_201,String.format(HttpResponses.MESSAGE_201,"Notification sended")));
    }

    @Operation(
            summary = "Get Session User Notifications REST API",
            description = "REST API to get Session User Notifications"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/notifications")
    public List<NotificationSessionUserDto> getBadgesAvailableToSend(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) Integer max
            ) {
        return notificationService.getBadgesAvailableToSend(from, to, max);
    }

}
