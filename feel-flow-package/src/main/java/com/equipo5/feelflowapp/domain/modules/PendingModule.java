package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.answers.Answer;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PendingModule {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",strategy="org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID uuid;

    @ManyToOne
    private Module module;

    @ManyToOne
    private RegularUser regularUser;

    @OneToMany
    private List<Answer> answers;

    @Enumerated(EnumType.STRING)
    private ModuleState moduleState;

    private LocalDateTime dateOfInit;

    private LocalDateTime dateOfEnd;
}
