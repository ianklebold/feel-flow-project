package com.equipo5.feelflowapp.domain.users;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.modules.Survey;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "regularuser_persona")
public class RegularUser extends User{
    @ManyToOne
    private Team team;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regularUser")
    List<Survey> surveys = new ArrayList<>();

    @Builder
    public RegularUser(UUID uuid, String name,String surname, String username, String password, List<Authority> authorities, Team team,List<Survey> surveys) {
        super(uuid, name, surname, username, password, authorities);
        this.team = team;
        this.surveys = surveys;
    }
}
