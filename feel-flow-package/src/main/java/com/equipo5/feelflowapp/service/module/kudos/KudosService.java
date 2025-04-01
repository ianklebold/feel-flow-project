package com.equipo5.feelflowapp.service.module.kudos;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.dto.modules.CreationKudosModuleDto;

import java.util.List;

public interface KudosService {
    KudosModule publishingModule (CreationKudosModuleDto creationKudosModule);

    void closeModule();

    List<KudosModule> getModulesBy(String name, Team team);
}
