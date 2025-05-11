package com.equipo5.feelflowapp.dto.notifications;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(
        name = "RecommendationDto",
        description = "Schema to hold recommendation information"
)
public record RecommendationDto(
        @Schema(description = "Title of recommendation", example = "Mensaje al TL") String title,
        @Schema(description = "Description of recommendation", example = "Este es un mensaje largo..") String body,
        LocalDateTime createdAt,
        List<String> suggestions
) {
}
