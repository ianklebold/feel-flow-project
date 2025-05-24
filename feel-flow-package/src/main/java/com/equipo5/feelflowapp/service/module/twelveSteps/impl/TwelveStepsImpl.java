package com.equipo5.feelflowapp.service.module.twelveSteps.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.CreationTwelveStepsModuleDto;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleAlreadyActiveException;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamException;
import com.equipo5.feelflowapp.repository.module.ModuleRepository;
import com.equipo5.feelflowapp.repository.module.ModuleTwelveStepsRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.module.twelveSteps.TwelveStepsService;
import com.equipo5.feelflowapp.service.point.PointService;
import com.equipo5.feelflowapp.service.survey.impl.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.TWELVE_STEPS;

@Service
public class TwelveStepsImpl implements TwelveStepsService {

    @Autowired
    private  TeamRepository teamRepository;


    @Autowired
    private ModuleTwelveStepsRepository moduleTwelveStepsRepository;

    @Autowired
    @Qualifier("TwelveStepsSurveyService")
    private  SurveyService surveyService;
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private PointService pointService;


    @Override
    @Transactional
    public TwelveStepsModule publishingModule(final CreationTwelveStepsModuleDto creationTwelveStepsModuleDto){
        //Verificar si existe encuesta activa para modulo
        Optional<Team> team = teamRepository.findById(creationTwelveStepsModuleDto.idTeam());

        if (team.isPresent()){
            var currentTeam = team.get();

            boolean existModuleActive =  isAnyModuleActive(TWELVE_STEPS.toString(),currentTeam.getModules());

            if (existModuleActive){
                //Error retornar excepcion
                throw new ModuleAlreadyActiveException("Actualmente se tiene un modulo de 12 pasos de la felicidad activo");
            }

            if( creationTwelveStepsModuleDto.dateAndTimeToClose().toLocalDateTime().isBefore( creationTwelveStepsModuleDto.dateAndTimeToClose().toLocalDateTime() )
                || creationTwelveStepsModuleDto.dateAndTimeToClose().toLocalDateTime().isEqual( creationTwelveStepsModuleDto.dateAndTimeToPublish().toLocalDateTime() )
            ){
                throw new ModuleException("La fecha de cierre es igual o antes que la fecha de creacion");
            }

            TwelveStepsModule twelveStepsModule = new TwelveStepsModule();
            twelveStepsModule.setCreationDate(LocalDate.now());
            twelveStepsModule.setModuleState(ModuleState.ACTIVE);
            twelveStepsModule.setName(TWELVE_STEPS.toString());
            twelveStepsModule.setTeam(currentTeam);
            twelveStepsModule.setDateAndTimeToClose( creationTwelveStepsModuleDto.dateAndTimeToClose() );
            twelveStepsModule.setDateAndTimeToPublish( creationTwelveStepsModuleDto.dateAndTimeToPublish() );
            twelveStepsModule.setDateAndTimeToPublish( creationTwelveStepsModuleDto.dateAndTimeToPublish() );

            //Crear las N encuestas para cada integrante del equipo
            // Parametros : Lista de miembros y modulo
            surveyService.createSurveis(currentTeam.getRegularUsers(),twelveStepsModule);

            return moduleTwelveStepsRepository.save(twelveStepsModule);
        }else {
            throw new NotFoundTeamException("Equipo no encontrado");
        }

    }

    private boolean isAnyModuleActive(final String name,final List<Module> modules) {
        return modules.stream()
                .filter(module -> module.getName().equals(name))
                .anyMatch(module -> module.getModuleState().toString().equals(ModuleState.ACTIVE.toString()));
    }

    @Override
    public double getValueForAnswer(String answer) {
        if (answer != null && !answer.isEmpty()) {
            if(answer.startsWith("1")){
                return 5d;
            } else if (answer.startsWith("2")) {
                return 4d;
            }else if (answer.startsWith("3")) {
                return 3d;
            } else if (answer.startsWith("4")) {
                return 2d;
            } else if (answer.startsWith("5")) {
                return 1d;
            }
        }
        return 0d;
    }

    @Override
    public double percentOfModuleCompleted(Team team) {

        AtomicInteger completed = new AtomicInteger();
        completed.set(0);

            int cantOfMembers = team.getRegularUsers().size();

            List<Module> modules =  this.moduleRepository.findModulesByNameAndTeamOrderByIdDescCreationDateDesc(ModuleNames.TWELVE_STEPS.toString(), team);

            if (!modules.isEmpty()){
                SurveyModule lastSurveyModule = (SurveyModule) modules.get(0);

                lastSurveyModule.getSurveys().forEach(
                        survey -> {
                            if (SurveyStateEnum.FINISHED.equals( survey.getSurveyStateEnum() ) ||  SurveyStateEnum.CLOSED.equals( survey.getSurveyStateEnum() )){
                                completed.getAndIncrement();
                            }
                        }
                );
                return (double) completed.get() / cantOfMembers;

            }

        return 0;
    }

    @Override
    public double happinessByTwelveStepsModule(Team team) {
        List<Module> modules =  this.moduleRepository.findModulesByNameAndTeamOrderByIdDescCreationDateDesc(TWELVE_STEPS.toString(), team);
        double points = 0d;

        if (!modules.isEmpty()) {
            SurveyModule lastSurveyModule = (SurveyModule) modules.get(0);
            double totalOfPoints = this.pointService.getTotalOfPointsPossibleTwelveSteps(lastSurveyModule.getSurveys(),team.getRegularUsers().size());

            points = team.getRegularUsers().stream().map(
                    user ->
                            lastSurveyModule.getSurveys()
                                    .stream()
                                    .filter( survey -> survey.getRegularUser().getUuid().equals( user.getUuid()) )
                                    .map( survey -> {
                                        return pointService.getPointsByTwelveStepsSurvey( survey );
                                    })
                                    .reduce(0d,Double::sum)

            ).reduce(0d,Double::sum);

            return ( points ) / totalOfPoints;

        }
        return 0;
    }

    @Override
    public double happinessByTwelveStepsModule(Team team, RegularUser regularUser) {
        List<Module> modules =  this.moduleRepository.findModulesByNameAndTeamOrderByIdDescCreationDateDesc(TWELVE_STEPS.toString(), team);
        double points = 0d;

        if (!modules.isEmpty()) {
            SurveyModule lastSurveyModule = (SurveyModule) modules.get(0);
            double totalOfPoints = this.pointService.getTotalOfPointsPossibleTwelveSteps(lastSurveyModule.getSurveys());

            points = lastSurveyModule.getSurveys()
                    .stream()
                    .filter( survey -> survey.getRegularUser().getUuid().equals( regularUser.getUuid()) )
                    .map( survey -> {
                        return pointService.getPointsByTwelveStepsSurvey( survey );
                    })
                    .reduce(0d,Double::sum);

            return ( points  ) / totalOfPoints;

        }
        return 0;
    }


}
