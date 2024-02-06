package com.equipo5.feelflowapp.service.module.twelveSteps;

import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;

import java.util.List;
import java.util.UUID;

public interface TwelveStepsService {

    void publishingModule(final UUID idTeam);

    void closeModule(final Long idModule);
}
