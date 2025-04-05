package com.equipo5.feelflowapp.dto.images;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImagesDto {
    private Long id;

    private String name;

    private String fileType;

    private String fileData;
}
