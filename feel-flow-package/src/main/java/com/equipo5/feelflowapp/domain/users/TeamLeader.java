package com.equipo5.feelflowapp.domain.users;

import com.equipo5.feelflowapp.domain.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "teamleader_persona")
public class TeamLeader extends User{

    @OneToOne(mappedBy = "teamLeader")
    private Team team;

    @Builder
    public TeamLeader(UUID uuid, String name, String email, String password, List<Authority> authorities, Team team) {
        super(uuid, name, email, password, authorities);
        this.team = team;
    }
}
