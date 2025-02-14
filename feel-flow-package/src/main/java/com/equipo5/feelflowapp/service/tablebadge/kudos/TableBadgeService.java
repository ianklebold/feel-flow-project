package com.equipo5.feelflowapp.service.tablebadge.kudos;

import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.modules.kudos.TableBadge;

import java.util.Optional;
import java.util.UUID;

public interface TableBadgeService {
    void createTableBadge(KudosModule kudosModule);

    Optional<TableBadge> getTableBadgeByModuleAndIdUser(KudosModule kudosModule, UUID idUser);

    boolean isPossibleAssignBadge(TableBadge tableBadge, BadgeName badgeName);

    void assignBadgeToTable(TableBadge tableBadge, Badge badge);

}
