package com.equipo5.feelflowapp.domain.users;

import com.devskiller.friendly_id.FriendlyId;
import com.equipo5.feelflowapp.domain.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InvitationTeam {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",strategy="org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID uuid;

    @OneToOne
    private RegularUser regularUser;

    @ManyToOne
    private Team team;

    private Date expirationDate;

    private boolean approved;

    private LocalDateTime dateOfApproval;

    public String getUuid() {
        return FriendlyId.toFriendlyId(this.uuid);
    }

    public boolean getExpirationDate() {
        Date d1 = new Date();
        return d1.compareTo(expirationDate) >= 0;
    }
}
