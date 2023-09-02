package com.equipo5.feelflowapp.dto.users;

import com.equipo5.feelflowapp.dto.enterprise.EnterpriseInfoHomeDTO;
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
    private EnterpriseInfoHomeDTO enterpriseInfoHomeDTO;
}
