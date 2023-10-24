package com.equipo5.feelflowapp.domain;

import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Team {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",strategy="org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID uuid;

    private String name;
    private String descriptionProject;

    @ManyToOne
    private EnterPrise enterPrise;

    @OneToOne(cascade = CascadeType.ALL)
    private TeamLeader teamLeader;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    private List<Module> modules = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    private List<RegularUser> regularUsers = new ArrayList<>();
}
