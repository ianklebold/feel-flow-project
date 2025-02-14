package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.dto.badges.BadgesAwardedDto;
import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.service.badges.BadgesService;
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
        badgesService.sendBadge(badgesAwardedDto);

        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(HttpResponses.STATUS_201,String.format(HttpResponses.MESSAGE_201,badgesAwardedDto.badgeName())));

    }

}
