package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.dto.images.ImagesDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "NikoNikoNoteDto",
        description = "Schema to hold niko niko note information"
)
public record NikoNikoNoteDto(
        @Schema(description = "Name of user", example = "Ian") String name,
        @Schema(description = "Surname of user", example = "Fernandez") String surname,
        @Schema(description = "User note", example = "Me fue fantastico") String note,
        @Schema(description = "User Image") ImagesDto imagesDto
) {
}
