package com.equipo5.feelflowapp.dto.notifications;

import com.equipo5.feelflowapp.dto.images.ImagesDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "NotificationNikoNikoDto",
        description = "Schema to hold notification information"
)
public record NotificationNikoNikoPanelDto(
        @Schema(description = "Regular User Name and Surname", example = "Ian Fernandez")  String regularUser,
        @Schema(description = "Regular User Image") ImagesDto imagesDto,
        @Schema(description = "Note", example = "Hoy fue un dia productivo") String message
) {}
