package com.equipo5.feelflowapp.service.module.nikoniko;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import com.equipo5.feelflowapp.dto.modules.CreationNikoNikoModule;

import java.util.List;

public interface NikoNikoService {
    NikoNikoModule publishingModule (CreationNikoNikoModule creationNikoNikoModule);
    double getValueByAnswer (String answer);
    double percentOfModuleCompleted(Team team);
}
