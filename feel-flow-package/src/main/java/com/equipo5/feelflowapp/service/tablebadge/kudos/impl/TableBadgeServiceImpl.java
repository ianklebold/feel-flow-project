package com.equipo5.feelflowapp.service.tablebadge.kudos.impl;

import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.modules.kudos.TableBadge;
import com.equipo5.feelflowapp.repository.tablebadge.TableBadgeRepository;
import com.equipo5.feelflowapp.service.tablebadge.kudos.TableBadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableBadgeServiceImpl implements TableBadgeService {

    private final TableBadgeRepository tableBadgeRepository;

    @Override
    public void createTableBadge(KudosModule kudosModule) {
        kudosModule
                .getTeam().getRegularUsers()
                    .forEach(regularUser -> {
                        TableBadge tableBadge = new TableBadge();
                        tableBadge.setKudosModule( kudosModule );
                        tableBadge.setTableBadgeOwner( regularUser );
                        tableBadgeRepository.save( tableBadge );

                        kudosModule.getTableBadge().add(tableBadge);
                    });

    }
}
