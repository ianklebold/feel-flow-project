package com.equipo5.feelflowapp.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminDTO {
    private String name;
    private String email;
    private String password;
    private String enterpriseName;
}
