package com.equipo5.feelflowapp.service.module.twelveSteps;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import com.equipo5.feelflowapp.dto.modules.CreationTwelveStepsModuleDto;

import java.util.List;


public interface TwelveStepsService {

    TwelveStepsModule publishingModule(final CreationTwelveStepsModuleDto creationTwelveStepsModuleDto);

    double getValueForAnswer(String answer);

    double percentOfModuleCompleted(Team team);


}
