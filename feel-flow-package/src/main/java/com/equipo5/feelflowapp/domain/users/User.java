package com.equipo5.feelflowapp.domain.users;


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
    private String surname;
    private String email;
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "id"))
    private List<Authority> authorities;

}
