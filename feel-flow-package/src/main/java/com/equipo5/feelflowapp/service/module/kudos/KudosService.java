package com.equipo5.feelflowapp.service.module.kudos;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.CreationKudosModuleDto;

import java.util.List;


public interface KudosService {
    KudosModule publishingModule (CreationKudosModuleDto creationKudosModule);

    KudosModule closeModule();

    boolean isModuleKudosAvailable();

    List<RegularUser> usersAwardedByModule(KudosModule kudosModule);

    double percentOfModuleCompleted(Team team);

    int countOfKudosSent(Team team);
    double happinessByKudosModule(Team team);
    String getEmotionalStateByKudosHappiness(double happiness);
}
