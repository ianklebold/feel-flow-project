package com.equipo5.feelflowapp.service.module.nikoniko.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import com.equipo5.feelflowapp.dto.modules.CreationNikoNikoModule;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleAlreadyActiveException;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamException;
import com.equipo5.feelflowapp.repository.module.ModuleNikoNikoRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.module.nikoniko.NikoNikoService;
import com.equipo5.feelflowapp.service.survey.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.NIKO_NIKO;
import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.TWELVE_STEPS;

@Service
public class NikoNikoServiceImpl implements NikoNikoService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    @Qualifier("NikoNikoSurveyServiceImpl")
    private SurveyService surveyService;

    @Autowired
    private ModuleNikoNikoRepository moduleNikoNikoRepository;


    @Override
    public void publishingModule(CreationNikoNikoModule creationNikoNikoModule) {

        Optional<Team> team = teamRepository.findById(creationNikoNikoModule.idTeam());

        if (team.isPresent() ){
            var currentTeam = team.get();
            boolean existModuleActive =  moduleService.isAnyModuleActive(TWELVE_STEPS.toString(),currentTeam.getModules());

            if (existModuleActive){
                //Error retornar excepcion
                throw new ModuleAlreadyActiveException("Actualmente se tiene un modulo de Niko Niko activo");
            }

            //Controlar que el tiempo timeToToResponseEndDay no sea antes que timeToToResponseStartDay
            if(creationNikoNikoModule.timeToToResponseEndDay().isBefore(creationNikoNikoModule.timeToToResponseStartDay())){
                throw new ModuleException("El tiempo de la habilitacion para la actividad al finalizar la jornada es antes que el del comienzo de jornada");
            }

            NikoNikoModule nikoNikoModule = new NikoNikoModule();
            nikoNikoModule.setCreationDate(LocalDate.now());
            nikoNikoModule.setModuleState(ModuleState.ACTIVE);
            nikoNikoModule.setName(NIKO_NIKO.toString());
            nikoNikoModule.setTeam(currentTeam);
            nikoNikoModule.setDateAndTimeToPublish(creationNikoNikoModule.dateAndTimeToPublish());
            nikoNikoModule.setDateAndTimeToClose(creationNikoNikoModule.dateAndTimeToClose());
            nikoNikoModule.setTimeToToResponseStartDay(nikoNikoModule.getTimeToToResponseStartDay());
            nikoNikoModule.setTimeToToResponseEndDay(nikoNikoModule.getTimeToToResponseEndDay());

            moduleNikoNikoRepository.save((nikoNikoModule));
        }else {
            throw new NotFoundTeamException("Equipo no encontrado");
        }

    }
}
