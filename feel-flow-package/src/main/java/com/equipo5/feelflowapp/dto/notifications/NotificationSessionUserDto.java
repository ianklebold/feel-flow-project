package com.equipo5.feelflowapp.dto.notifications;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "NotificationSessionUserDto",
        description = "Schema to hold notification information"
)
public record NotificationSessionUserDto(
        @Schema(description = "Title of notification", example = "Hello sam is me")  String title,
        @Schema(description = "Body of notification", example = "The present message is going to...")  String body,
        @Schema(description = "Date and Time of Notification was create") LocalDateTime createdAt
) {}
