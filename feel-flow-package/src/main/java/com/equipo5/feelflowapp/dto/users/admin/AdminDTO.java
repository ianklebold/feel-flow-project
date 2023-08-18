package com.equipo5.feelflowapp.dto.users.admin;

import com.equipo5.feelflowapp.dto.enterprise.EnterpriseDTO;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminDTO {
    private UUID uuid;
    private String name;
    private String email;
    private String password;
    private EnterpriseDTO enterpriseDTO;
}
