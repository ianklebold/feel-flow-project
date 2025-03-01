package com.equipo5.feelflowapp.domain.images;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "media_image")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MediaImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, updatable = true)
    private String name;

    @Column(name = "fileType", nullable = false, updatable = true)
    private String fileType;

    @Lob
    @Column(name = "fileData", columnDefinition="LONGBLOB", nullable = false, updatable = true)
    private String fileData;
}
