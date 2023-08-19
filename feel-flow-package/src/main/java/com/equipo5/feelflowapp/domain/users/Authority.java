package com.equipo5.feelflowapp.domain.users;

import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TeamRoles teamRoles;

    @ManyToMany(mappedBy = "authorities")
    private List<User> users;
}
