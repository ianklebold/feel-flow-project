package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.dto.users.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(
        name = "ModuleAndUsersDto",
        description = "Schema to hold module and users"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleAndUsersDto {
    private SimpleModuleDto moduleDto;
    private List<UserDTO> usersDto;
}
