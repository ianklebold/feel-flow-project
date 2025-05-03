package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.dto.badges.*;
import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.service.badges.BadgesService;
import com.equipo5.feelflowapp.service.module.kudos.KudosService;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import com.equipo5.feelflowapp.service.notification.kudos.KudosNotificationService;
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

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Module Kudos REST APIs",
        description = "REST APIs in Project to CREATE, UPDATE, GET AND DELETE Kudos Module"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = BadgesController.MODULE_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class BadgesController {
    public  static final String MODULE_PATH = "/api/v1/badges";

    private final BadgesService badgesService;

    private final KudosService kudosService;

    private final NotificationService notificationService;

    private final KudosNotificationService kudosNotificationService;

    @Operation(
            summary = "Send Badge REST API",
            description = "REST API to send badge to other member of team"
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
    public ResponseEntity<ResponseDto> sendBadge(@RequestBody BadgesAwardedDto badgesAwardedDto) {
        Badge badge = badgesService.sendBadge(badgesAwardedDto);
        if(badge != null) {
            KudosModule kudosModule = kudosService.closeModule();
            kudosNotificationService.sendKudosNotificationToLeader(badge);

            if (kudosModule != null){
                kudosNotificationService.sendKudosClosedNotificationToLeader(kudosModule);
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(HttpResponses.STATUS_404,HttpResponses.MESSAGE_404_NOT_FOUND));
        }

    }

    @Operation(
            summary = "Get Badge availables to sent REST API",
            description = "REST API to get the badges availables to send"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
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
    @GetMapping("/available")
    public List<BadgesAvailableDto> getBadgesAvailableToSend() {
        return badgesService.getBadgesAvailableToSend();
    }

    @Operation(
            summary = "Get Badges REST API",
            description = "REST API to get the badges"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
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
    @GetMapping("/awarded")
    public List<BadgeWithNumberOfBadgesDto> getBadges() {
        return badgesService.getBadgesAwarded();
    }

    @Operation(
            summary = "Get Badges REST API awarded by user id",
            description = "REST API to get the badges"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
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
    @GetMapping("/awarded/user/{user_id}")
    public List<BadgeWithNumberOfBadgesDto> getBadges(@PathVariable(value = "user_id") UUID userId ) {
        return badgesService.getBadgesAwarded(userId);
    }

    @Operation(
            summary = "Get Badges by Team REST API",
            description = "REST API to get the badges"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
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
    @GetMapping()
    public List<BadgeTeamDto> getBadgesByTeam() {
        return badgesService.getBadgeTeams();
    }
}
