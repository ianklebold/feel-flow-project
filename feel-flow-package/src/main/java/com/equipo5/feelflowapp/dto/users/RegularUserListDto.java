package com.equipo5.feelflowapp.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(
        name = "RegularUserListDto",
        description = "Schema to hold Regular User information"
)
public record RegularUserListDto(
        @Schema(description = "Regular User's Id") UUID uuid,
        @Schema(description = "Regular User's name") String name,
        @Schema(description = "Regular User's surname") String surname,
        @Schema(description = "Regular User's username") String username
) {
}
