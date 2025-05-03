package com.equipo5.feelflowapp.dto.notifications;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Schema(
        name = "NotificationDto",
        description = "Schema to hold notification information"
)
public record NotificationDto(
        @Schema(description = "Title of notification", example = "Hello sam is me")  String title,
        @Schema(description = "Body of notification", example = "The present message is going to...")  String body,
        @Schema(description = "Type of notification") NotificationTypeEnum notificationType,
        @Schema(description = "Creation date") LocalDateTime creationDate
) {}
