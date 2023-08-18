package com.equipo5.feelflowapp.domain.users;

import com.equipo5.feelflowapp.domain.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "regularuser_persona")
public class RegularUser extends User{
    @ManyToOne
    private Team team;
}