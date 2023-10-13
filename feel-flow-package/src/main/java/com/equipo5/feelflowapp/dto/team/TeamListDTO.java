package com.equipo5.feelflowapp.dto.team;



import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamListDTO {
    private UUID uuid;

    private String nameTeam;

    private String descriptionTeam;

    private TeamLeaderDTO teamLeaderDTO;

    private List<UserDTO> regularUsers = new ArrayList<>();
}
