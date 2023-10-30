package com.equipo5.feelflowapp.dto.users.invitation;

import com.equipo5.feelflowapp.dto.team.TeamDTO;
import com.equipo5.feelflowapp.dto.users.RegularUserDTO;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvitationTeamDTO {
    private String uuid;
    private TeamDTO teamDTO;
    private RegularUserDTO regularUserDTO;
    private boolean isApproved;
    private boolean isExpirated;
}
