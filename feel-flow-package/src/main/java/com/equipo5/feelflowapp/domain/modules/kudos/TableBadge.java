package com.equipo5.feelflowapp.domain.modules.kudos;

import com.equipo5.feelflowapp.domain.users.RegularUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "table_badge")
public class TableBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Badge> badgeFriendHands = new ArrayList<>();

    @OneToOne
    private Badge badgeResolutorStar;

    @OneToOne
    private Badge badgePositiveEnergy;

    @OneToOne
    private Badge masterOfDetail;

    @ManyToOne
    private RegularUser tableBadgeOwner;

    @ManyToOne
    private KudosModule kudosModule;

    private LocalDate tableBadgeClosedDate;
}
