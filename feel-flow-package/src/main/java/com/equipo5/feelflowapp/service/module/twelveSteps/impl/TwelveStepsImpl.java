package com.equipo5.feelflowapp.service.module.twelveSteps.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import com.equipo5.feelflowapp.exception.module.ModuleAlreadyActiveException;
import com.equipo5.feelflowapp.repository.module.ModuleTwelveStepsRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.module.twelveSteps.TwelveStepsService;
import com.equipo5.feelflowapp.service.survey.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.TWELVE_STEPS;

@Service
public class TwelveStepsImpl implements TwelveStepsService {

    @Autowired
    private  TeamRepository teamRepository;

    @Autowired
    private  ModuleService moduleService;

    @Autowired
    private ModuleTwelveStepsRepository moduleTwelveStepsRepository;

    @Autowired
    @Qualifier("TwelveStepsSurveyService")
    private  SurveyService surveyService;



    @Override
    @Transactional
    public void publishingModule(final UUID uuidTeam) {
        //Verificar si existe encuesta activa para modulo
        Optional<Team> team = teamRepository.findById(uuidTeam);

        if (team.isPresent()){
            var currentTeam = team.get();

            boolean existModuleActive =  moduleService.isAnyModuleActive(TWELVE_STEPS.toString(),currentTeam.getModules());

            if (existModuleActive){
                //Error retornar excepcion
                throw new ModuleAlreadyActiveException("Actualmente se tiene un modulo de 12 pasos de la felicidad activo");
            }

            TwelveStepsModule twelveStepsModule = new TwelveStepsModule();
            twelveStepsModule.setCreationDate(LocalDate.now());
            twelveStepsModule.setModuleState(ModuleState.ACTIVE);
            twelveStepsModule.setName(TWELVE_STEPS.toString());
            twelveStepsModule.setTeam(currentTeam);

            //Crear las N encuestas para cada integrante del equipo
            // Parametros : Lista de miembros y modulo
            surveyService.createSurveis(currentTeam.getRegularUsers(),twelveStepsModule);

            moduleTwelveStepsRepository.save(twelveStepsModule);
        }

    }
}
