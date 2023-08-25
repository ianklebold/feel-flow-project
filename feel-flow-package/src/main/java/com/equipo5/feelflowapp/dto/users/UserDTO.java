package com.equipo5.feelflowapp.dto.users;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private UUID uuid;
    private String name;
    private String surname;
    private String username;
}
