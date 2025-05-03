package com.equipo5.feelflowapp.dto.notifications;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(
        name = "NotificationDto",
        description = "Schema to hold notification information"
)
public record NotificationKudosPanelDto(
        @Schema(description = "Name sender", example = "Ian")  String from,
        @Schema(description = "Name receiver", example = "Santi")  String to,
        @Schema(description = "Type Badge", example = "Manos amigas") String badgeName,
        @Schema(description = "creationDate") LocalDate creationDate
) {}