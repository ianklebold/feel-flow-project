package com.equipo5.feelflowapp.domain;

import com.equipo5.feelflowapp.domain.users.Admin;
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
public class EnterPrise {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",strategy="org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID uuid;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Admin admin;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enterPrise")
    private List<Team> team = new ArrayList<>();
}
