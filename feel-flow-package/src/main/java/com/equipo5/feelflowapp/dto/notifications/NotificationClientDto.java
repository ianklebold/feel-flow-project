package com.equipo5.feelflowapp.dto.notifications;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

@Schema(
        name = "NotificationClientDto",
        description = "Schema to hold the client notification information"
)
public record NotificationClientDto(
        @Schema(description = "Title of notification", example = "Hello sam is me")  String title,
        @Schema(description = "Body of notification", example = "The present message is going to...")  String body,
        @Schema(description = "UUID of team", example = "UUID of team")UUID uuidTeam
) {}
