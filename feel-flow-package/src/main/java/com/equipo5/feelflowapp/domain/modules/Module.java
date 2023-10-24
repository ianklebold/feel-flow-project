package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "module_model")
@Inheritance(strategy = InheritanceType.JOINED)
public class Module {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",strategy="org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID uuid;

    private String name;

    @Enumerated(EnumType.STRING)
    private ModuleState moduleState;

    @ManyToOne
    private Team team;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    private List<PendingModule> pendingModules = new ArrayList<>();

    private LocalDateTime dateOfInit;

    private LocalDateTime dateOfEnd;
}
