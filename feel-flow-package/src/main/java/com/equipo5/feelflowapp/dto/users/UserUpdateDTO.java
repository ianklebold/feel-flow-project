package com.equipo5.feelflowapp.dto.users;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {
    private String name;
    private String surname;
    private String username;
}
