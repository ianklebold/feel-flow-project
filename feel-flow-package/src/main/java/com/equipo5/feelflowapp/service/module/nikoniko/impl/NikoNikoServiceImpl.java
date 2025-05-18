package com.equipo5.feelflowapp.service.module.nikoniko.impl;

import com.equipo5.feelflowapp.constants.module.nikoniko.ResponseConstantsNikoNiko;
import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import com.equipo5.feelflowapp.dto.modules.CreationNikoNikoModule;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleAlreadyActiveException;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamException;
import com.equipo5.feelflowapp.repository.module.ModuleNikoNikoRepository;
import com.equipo5.feelflowapp.repository.module.ModuleRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.module.nikoniko.NikoNikoService;
import com.equipo5.feelflowapp.service.point.PointService;
import com.equipo5.feelflowapp.service.survey.impl.SurveyService;
import com.equipo5.feelflowapp.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.NIKO_NIKO;

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

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private PointService pointService;

    @Override
    public NikoNikoModule publishingModule(CreationNikoNikoModule creationNikoNikoModule) {

        Optional<Team> team = teamRepository.findById(creationNikoNikoModule.idTeam());

        if (team.isPresent() ){
            var currentTeam = team.get();
            boolean existModuleActive =  moduleService.isAnyModuleActive(NIKO_NIKO.toString(),currentTeam.getModules());

            if (existModuleActive){
                //Error retornar excepcion
                throw new ModuleAlreadyActiveException("Actualmente se tiene un modulo de Niko Niko activo");
            }

            //Controlar que el tiempo timeToToResponseEndDay no sea antes que timeToToResponseStartDay
            if(creationNikoNikoModule.timeToToResponseEndDay().toLocalTime().isBefore(creationNikoNikoModule.timeToToResponseStartDay().toLocalTime())){
                throw new ModuleException("El tiempo de la habilitacion para la actividad al finalizar la jornada es antes que el del comienzo de jornada");
            }

            NikoNikoModule nikoNikoModule = new NikoNikoModule();
            nikoNikoModule.setCreationDate(LocalDate.now());
            nikoNikoModule.setModuleState(ModuleState.ACTIVE);
            nikoNikoModule.setName(NIKO_NIKO.toString());
            nikoNikoModule.setTeam(currentTeam);
            nikoNikoModule.setDateAndTimeToPublish(creationNikoNikoModule.dateAndTimeToPublish());
            nikoNikoModule.setDateAndTimeToClose(creationNikoNikoModule.dateAndTimeToClose());
            nikoNikoModule.setTimeToToResponseStartDay(creationNikoNikoModule.timeToToResponseStartDay());
            nikoNikoModule.setTimeToToResponseEndDay(creationNikoNikoModule.timeToToResponseEndDay());

            return moduleNikoNikoRepository.save((nikoNikoModule));
        }else {
            throw new NotFoundTeamException("Equipo no encontrado");
        }

    }

    @Override
    public double getValueByAnswer(String answer) {
        return switch (answer) {
            case ResponseConstantsNikoNiko.ANSWERS_1_POOL_NIKO_NIKO -> 2d;
            case ResponseConstantsNikoNiko.ANSWERS_2_POOL_NIKO_NIKO -> 1d;
            case ResponseConstantsNikoNiko.ANSWERS_4_POOL_NIKO_NIKO -> -1d;
            case ResponseConstantsNikoNiko.ANSWERS_5_POOL_NIKO_NIKO -> -2d;
            default -> 0d;
        };
    }

    @Override
    public double percentOfModuleCompleted(Team team) {
        final int[] completed = {0};
        AtomicInteger completedByUser = new AtomicInteger();
            int cantOfMembers = team.getRegularUsers().size();

            List<Module> modules =  this.moduleRepository.findModulesByNameAndTeamOrderByIdDescCreationDateDesc(NIKO_NIKO.toString(), team);

            if (!modules.isEmpty()){
                SurveyModule lastSurveyModule = (SurveyModule) modules.get(0);
                long cantOfSurveys = daysBetween(lastSurveyModule.getDateAndTimeToPublish(), lastSurveyModule.getDateAndTimeToClose());

                //Obtener la cantidad de encuestas posibles.. NencuestasPosibles
                //Obtener la cantiudad de encuestas completadas por usuario.. XencuestasCompletadas
                //Porcentaje de xencuestasCompletadas/NecuestasPosibles = porcentaje Modulo completado por usuario

                // Sumatoria de  porcentaje Modulo completado por usuario divido la cantidad de miembros da el porcentaje total.

                team.getRegularUsers().forEach(user -> {
                    lastSurveyModule.getSurveys()
                            .stream()
                            .filter(survey -> survey.getRegularUser().getUuid().equals( user.getUuid() ))
                            .forEach( survey -> {
                                if (survey.getActivities().size() == 2 && isActivityCompleted( survey.getActivities().get(0) ) && isActivityCompleted( survey.getActivities().get(1) )){
                                    completedByUser.incrementAndGet();
                                }
                            });
                    completed[0] = (int) (completed[0] + ( completedByUser.get() / cantOfSurveys ));
                    completedByUser.set(0);
                });

                return (double) completed[0] /cantOfMembers;

            }

        return 0;
    }

    @Override
    public double happinessByNikoNikoModule(Team team) {
        List<Module> modules =  this.moduleRepository.findModulesByNameAndTeamOrderByIdDescCreationDateDesc(NIKO_NIKO.toString(), team);
        double points = 0d;
        if (!modules.isEmpty()){
            SurveyModule lastSurveyModule = (SurveyModule) modules.get(0);
            double totalOfPoints = this.pointService.getTotalOfPointsPossibleNikoNiko(lastSurveyModule.getSurveys(),team.getRegularUsers().size());

            points = team.getRegularUsers().stream().map(
                    user ->
                         lastSurveyModule.getSurveys()
                                .stream()
                                .filter( survey -> survey.getRegularUser().getUuid().equals( user.getUuid()) )
                                .map( survey -> {
                                    return pointService.getPointsByNikoNikoSurvey( survey );
                                })
                                .reduce(0d,Double::sum)

            ).reduce(0d,Double::sum);

            return ( points / team.getRegularUsers().size() ) / totalOfPoints;
        }

        return points;
    }

    private boolean isActivityCompleted(Activity activity){
        return ActivityState.FINISHED.equals(activity.getActivityState());
    }

    public static long daysBetween(Timestamp ts1, Timestamp ts2) {
        LocalDate d1 = ts1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate d2 = ts2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return ChronoUnit.DAYS.between(d1, d2);
    }

}
