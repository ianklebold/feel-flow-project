package com.equipo5.feelflowapp.domain.users;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.modules.PendingModule;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

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

    @OneToMany(mappedBy = "regularUser")
    private List<PendingModule> pendingModule;

    @Builder
    public RegularUser(UUID uuid, String name,String surname, String username, String password, List<Authority> authorities, Team team) {
        super(uuid, name, surname, username, password, authorities);
        this.team = team;
    }
}
