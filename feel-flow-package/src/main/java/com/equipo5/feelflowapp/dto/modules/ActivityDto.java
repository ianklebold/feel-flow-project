package com.equipo5.feelflowapp.dto.modules;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ActivityDto",
        description = "Schema to hold activity information"
)
public record ActivityDto(
        @Schema(description = "Question of activity", example = "Who i am?") String question,
        @Schema(description = "Answer of activity", example = "I am Ian") String answer
) {}
