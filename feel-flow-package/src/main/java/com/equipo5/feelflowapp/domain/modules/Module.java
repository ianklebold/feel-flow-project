package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "module")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate creationDate;

    private LocalDate moduleClosedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateAndTimeToPublish;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateAndTimeToClose;

    @Enumerated(EnumType.STRING)
    private ModuleState moduleState;

    @ManyToOne
    private Team team;
}
