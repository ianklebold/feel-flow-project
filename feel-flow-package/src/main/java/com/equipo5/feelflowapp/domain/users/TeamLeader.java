package com.equipo5.feelflowapp.domain.users;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.images.MediaImage;
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
    public TeamLeader(UUID uuid, String name, String surname, String username, String password, String country, String phoneNumber, String description, List<Authority> authorities, MediaImage mediaImage, Team team) {
        super(uuid, name, surname, username, password, country, phoneNumber, description, authorities, mediaImage);
        this.team = team;
    }
}
