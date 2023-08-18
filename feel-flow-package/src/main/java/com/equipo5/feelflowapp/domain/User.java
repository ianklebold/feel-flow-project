package com.equipo5.feelflowapp.domain;


import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user_model")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",strategy="org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID uuid;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private TeamRoles role;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_authority",
    joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "ID")},
    inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID",referencedColumnName = "ID")})
    private List<Authority> authorities;

}
