package com.equipo5.feelflowapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "teamleader_persona")
public class TeamLeader extends User{

    @OneToOne(mappedBy = "teamLeader")
    private Team team;
}
