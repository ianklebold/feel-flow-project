package com.equipo5.feelflowapp.domain;

import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TeamRoles teamRoles;

    @ManyToMany(mappedBy = "authorities")
    private List<User> users;
}
