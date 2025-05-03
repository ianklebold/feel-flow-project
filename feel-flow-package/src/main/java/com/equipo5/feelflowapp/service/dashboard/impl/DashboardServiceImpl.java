package com.equipo5.feelflowapp.service.dashboard.impl;

import com.equipo5.feelflowapp.constants.module.twelvesteps.QuestionsConstantsTwelveSteps;
import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.modules.ActivityNikoNiko;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.dto.dashboard.general.GeneralSummaryDto;
import com.equipo5.feelflowapp.dto.dashboard.kudos.KudosSummaryData;
import com.equipo5.feelflowapp.dto.dashboard.nikoniko.NikoNikoAvgData;
import com.equipo5.feelflowapp.dto.dashboard.TeamAndModulesDto;
import com.equipo5.feelflowapp.dto.dashboard.nikoniko.NikoNikoSummaryData;
import com.equipo5.feelflowapp.dto.modules.ModuleAndUsersDto;
import com.equipo5.feelflowapp.dto.modules.TwelveStepsResponseAvgDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationKudosPanelDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationNikoNikoPanelDto;
import com.equipo5.feelflowapp.dto.tablebadge.TableBadgeAwardedDto;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import com.equipo5.feelflowapp.dto.team.TeamListDTO;
import com.equipo5.feelflowapp.mappers.modules.ModuleMapper;
import com.equipo5.feelflowapp.mappers.modules.kudos.KudosSummaryDataMapper;
import com.equipo5.feelflowapp.mappers.users.UserMapper;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.service.dashboard.DashboardService;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.module.nikoniko.NikoNikoService;
import com.equipo5.feelflowapp.service.module.twelveSteps.TwelveStepsService;
import com.equipo5.feelflowapp.service.notification.kudos.KudosNotificationService;
import com.equipo5.feelflowapp.service.notification.nikoniko.NikoNikoNotificationService;
import com.equipo5.feelflowapp.service.survey.impl.SurveyService;
import com.equipo5.feelflowapp.service.tablebadge.kudos.TableBadgeService;
import com.equipo5.feelflowapp.service.team.TeamService;
import com.equipo5.feelflowapp.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.equipo5.feelflowapp.constants.module.nikoniko.QuestionsConstantsNikoNiko.QUESTIONS_1_POOL_NIKO_NIKO;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final SurveyService surveyService;
    private final TeamService teamService;
    private final TeamRepository teamRepository;
    private final TwelveStepsService twelveStepsService;
    private final ModuleService moduleService;

    private final ModuleMapper moduleMapper;
    private final UserMapper userMapper;
    private final NikoNikoService nikoNikoService;

    private final TableBadgeService tableBadgeService;
    private final KudosSummaryDataMapper kudosSummaryDataMapper;

    private final UserService userService;
    private final KudosNotificationService kudosNotificationService;
    private final NikoNikoNotificationService nikoNikoNotificationService;

    private final UserRepository userRepository;

    @Autowired
    public DashboardServiceImpl(@Qualifier("SurveyService") SurveyService surveyService, TeamService teamService, TeamRepository teamRepository, TwelveStepsService twelveStepsService, ModuleService moduleService, ModuleMapper moduleMapper, UserMapper userMapper, NikoNikoService nikoNikoService, TableBadgeService tableBadgeService, KudosSummaryDataMapper kudosSummaryDataMapper, UserService userService, UserRepository userRepository, KudosNotificationService kudosNotificationService, NikoNikoNotificationService nikoNikoNotificationService) {
        this.surveyService = surveyService;
        this.teamService = teamService;
        this.teamRepository = teamRepository;
        this.twelveStepsService = twelveStepsService;
        this.moduleService = moduleService;
        this.moduleMapper = moduleMapper;
        this.userMapper = userMapper;
        this.nikoNikoService = nikoNikoService;
        this.tableBadgeService = tableBadgeService;
        this.kudosSummaryDataMapper = kudosSummaryDataMapper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.kudosNotificationService = kudosNotificationService;
        this.nikoNikoNotificationService = nikoNikoNotificationService;
    }

    @Override
    public List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedData() {

        //Si no es admin --> Debe ver su propio equipo
        List<TeamListDTO> teamListDTOS = this.teamService.getAllTeams();
        List<TwelveStepsResponseAvgDto> twelveStepsResponseAvgDtos = new ArrayList<>();
        if (teamListDTOS.size() == 1){
            Team team = teamRepository.getReferenceById(teamListDTOS.get(0).getUuid());
            List<Survey> surveys = new ArrayList<>();

            surveys = this.surveyService.getSurveysByModule(ModuleNames.TWELVE_STEPS.toString(), team);
            twelveStepsResponseAvgDtos = getTwelveStepsResponseAvgDto(surveys);
        } else if (teamListDTOS.size() > 1) {

            List<Team> teams = teamRepository.findAllById(
                    teamListDTOS.stream().map(TeamListDTO::getUuid).collect(Collectors.toList())
            );
            List<Survey> surveys = new ArrayList<>();
            teams.forEach(team -> {
                        surveys.addAll(this.surveyService.getSurveysByModule(ModuleNames.TWELVE_STEPS.toString(), team));
                    });
            twelveStepsResponseAvgDtos = getTwelveStepsResponseAvgDto(surveys);
        }


        twelveStepsResponseAvgDtos.forEach( result -> result.setAverage( getPercentByScale(result.getAverage(), 5d) ) );
        return twelveStepsResponseAvgDtos;
    }

    @Override
    public List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedDataForCurrentUser() {
        var username = userService.getUsernameByCurrentUser();
        var regularUser = userRepository.findByUsername(username);
        List<TwelveStepsResponseAvgDto> twelveStepsResponseAvgDtos;

        if(regularUser.isPresent()){
            List<Survey> surveys;

            surveys = this.surveyService.getSurveysByModuleAndUserId(ModuleNames.TWELVE_STEPS, regularUser.get().getUuid());

            if (!surveys.isEmpty()) {
                twelveStepsResponseAvgDtos = getTwelveStepsResponseAvgDto(surveys);
            }else{
                twelveStepsResponseAvgDtos = getTwelveStepsResponseWithAvgZero();
            }

        }else{
            twelveStepsResponseAvgDtos = getTwelveStepsResponseWithAvgZero();
        }
        return twelveStepsResponseAvgDtos;
    }

    public List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedDataBySurvey(Survey survey) {
        if(survey != null){
            return getTwelveStepsResponseAvgDto(List.of(survey));
        }
        return null;
    }

    private List<TwelveStepsResponseAvgDto> getTwelveStepsResponseWithAvgZero(){
        return QuestionsConstantsTwelveSteps.QUESTIONS_CATEGORY_TWELVE_STEPS
                .stream()
                .map( category -> TwelveStepsResponseAvgDto.builder()
                        .categoryName(category)
                        .average(0)
                        .build()
                ).toList();
    }

    @Override
    public List<TeamAndModulesDto> getTeamsAndModulesData(boolean isAdmin,ModuleNames nameModule) {
        List<TeamAndModulesDto> teamAndModulesDtos = new ArrayList<>();
        List<TeamDTO> teamDto = this.teamService.getTeamsByRole(isAdmin);
        teamDto.forEach(team ->
            teamAndModulesDtos.add(
                    new TeamAndModulesDto(
                            team,
                            moduleService.getModulesSurveysByTeamIdAndModuleName(ModuleNames.TWELVE_STEPS, team.getUuid())
                    )
            )
        );

        return teamAndModulesDtos;
    }

    @Override
    public List<ModuleAndUsersDto> getModuleAndUsersData(ModuleNames nameModule, Boolean isAdmin) {

        //Si es admin entonces devolver todos los modulos de toda la empresa con todos sus usuarios.
        List<Module> modules = moduleService.getAllModules(nameModule, isAdmin);
        //Si no es admin entonces devolver todos los modulos del equipo
        if ( !modules.isEmpty() ){
            return modules.stream()
                    .map( module ->
                            new ModuleAndUsersDto(
                                    moduleMapper.moduleToSimpleModuleDto(module),
                                    module.getTeam().getRegularUsers().stream().map(userMapper::userToUserDto).toList()
                            )
                    )
                    .toList();
        }


        return List.of();
    }


    @Override
    public NikoNikoSummaryData getEmotionalTrendDataAvg() {
        List<TeamListDTO> teamListDTOS = this.teamService.getAllTeams();
        int[] countOfResponseStartDay = {1,1,1,1,1};
        int[] countOfResponseEndDay = {1,1,1,1,1};
        List<ActivityNikoNiko> activityStartOfDayNikoNiko = new ArrayList<>();
        List<ActivityNikoNiko> activityEndOfDayNikoNiko = new ArrayList<>();
        List<Survey> surveys = new ArrayList<>();

        if (teamListDTOS.size() == 1){
            Team team = teamRepository.getReferenceById(teamListDTOS.get(0).getUuid());
            surveys = this.surveyService.getSurveysByModule(ModuleNames.NIKO_NIKO.toString(), team);

        } else if (teamListDTOS.size() > 1) {
            List<Team> teams = teamRepository.findAllById(
                    teamListDTOS.stream().map(TeamListDTO::getUuid).collect(Collectors.toList())
            );

            surveys = teams.stream().flatMap(team ->
                 this.surveyService.getSurveysByModule(ModuleNames.NIKO_NIKO.toString(), team).stream()
            ).toList();
        }

        surveys
                .forEach(survey -> {
                    var activity = survey.getActivities().get(0);
                    var activity2 = survey.getActivities().get(1);
                    if (activity instanceof ActivityNikoNiko){
                        addToActivityNikoNikoList((ActivityNikoNiko) activity, activityStartOfDayNikoNiko, activityEndOfDayNikoNiko);
                    }
                    if (activity2 instanceof ActivityNikoNiko){
                        addToActivityNikoNikoList((ActivityNikoNiko) activity2, activityStartOfDayNikoNiko, activityEndOfDayNikoNiko);
                    }
                }
                );

        return new NikoNikoSummaryData(
                getNikoNikoResponseAvgDto(activityStartOfDayNikoNiko, countOfResponseStartDay),
                getNikoNikoResponseAvgDto(activityEndOfDayNikoNiko, countOfResponseEndDay)
        );
    }

    private void addToActivityNikoNikoList(ActivityNikoNiko activityNikoNiko, List<ActivityNikoNiko> activityStartOfDayNikoNiko, List<ActivityNikoNiko> activityEndOfDayNikoNiko){
        if (activityNikoNiko.getQuestion().equals(QUESTIONS_1_POOL_NIKO_NIKO)){
            activityStartOfDayNikoNiko.add(activityNikoNiko);
        }else {
            activityEndOfDayNikoNiko.add(activityNikoNiko);
        }
    }

    @Override
    public List<KudosSummaryData> getKudosData() {
        List<TeamListDTO> teamListDTOS = this.teamService.getAllTeams();
        List<Survey> surveys = new ArrayList<>();
        List<TableBadgeAwardedDto> tableBadgeAwardedDtos = List.of();
        if (teamListDTOS.size() == 1){
            Team team = teamRepository.getReferenceById(teamListDTOS.get(0).getUuid());
            tableBadgeAwardedDtos = this.tableBadgeService.getTableBadgeDto(team);


        } else if (teamListDTOS.size() > 1) {
            List<Team> teams = teamRepository.findAllById(
                    teamListDTOS.stream().map(TeamListDTO::getUuid).collect(Collectors.toList())
            );
            tableBadgeAwardedDtos = teams.stream()
                    .flatMap(team -> this.tableBadgeService.getTableBadgeDto(team).stream())
                    .toList();
        }
        return tableBadgeAwardedDtos.stream()
                .map(tableBadgeAwardedDto -> {
                        KudosSummaryData kudosSummaryData = kudosSummaryDataMapper.tableBadgeAwardedDtoToKudosSummaryData(tableBadgeAwardedDto);
                        kudosSummaryData.setCantBadges( this.tableBadgeService.getNumberTotalOfBadgesBy(tableBadgeAwardedDto) );
                        return kudosSummaryData;
                })
                .toList();
    }

    private List<NikoNikoAvgData> getNikoNikoResponseAvgDto(List<ActivityNikoNiko> activityNikoNikos, int[] countOfResponse) {

        List<NikoNikoAvgData> nikoNikoAvgDataList = initializeNikoNikoResponseAvgDto();

        activityNikoNikos.stream()
                .filter(activityNikoNiko -> activityNikoNiko.getAnswer() != null)
                .filter( activityNikoNiko -> !DayOfWeek.SATURDAY.toString().equals(activityNikoNiko.getDayOfWeek().toString()) && !DayOfWeek.SUNDAY.toString().equals(activityNikoNiko.getDayOfWeek().toString()) )
                .forEach(activityNikoNiko -> {
                    NikoNikoAvgData nikoNikoAvgData = getNikoNikoAvgByDayOfWeek(nikoNikoAvgDataList, activityNikoNiko.getDayOfWeek());
                    nikoNikoAvgData.setAvg( nikoNikoAvgData.getAvg() +  nikoNikoService.getValueByAnswer(activityNikoNiko.getAnswer()));
                    sumByDayOfWeek(countOfResponse, activityNikoNiko.getDayOfWeek());
                });

        calculateAverageOfValues(nikoNikoAvgDataList, countOfResponse);

        return nikoNikoAvgDataList;

    }

    private List<NikoNikoAvgData> initializeNikoNikoResponseAvgDto(){
        List<NikoNikoAvgData> nikoNikoAvgDataList = new ArrayList<>();
        nikoNikoAvgDataList.add(new NikoNikoAvgData(DayOfWeek.MONDAY, 0d));
        nikoNikoAvgDataList.add(new NikoNikoAvgData(DayOfWeek.TUESDAY, 0d));
        nikoNikoAvgDataList.add(new NikoNikoAvgData(DayOfWeek.WEDNESDAY, 0d));
        nikoNikoAvgDataList.add(new NikoNikoAvgData(DayOfWeek.THURSDAY, 0d));
        nikoNikoAvgDataList.add(new NikoNikoAvgData(DayOfWeek.FRIDAY, 0d));
        return nikoNikoAvgDataList;
    }

    private void sumByDayOfWeek(int[] countOfResponse, DayOfWeek dayOfWeek){
        switch (dayOfWeek){
            case MONDAY:
                countOfResponse[0] = countOfResponse[0] + 1;
                break;
            case TUESDAY:
                countOfResponse[1] = countOfResponse[1] + 1;
                break;
            case WEDNESDAY:
                countOfResponse[2] = countOfResponse[2] + 1;
                break;
            case THURSDAY:
                countOfResponse[3] = countOfResponse[3] + 1;
                break;
            case FRIDAY:
                countOfResponse[4] = countOfResponse[4] + 1;
                break;
        }
    }

    private void calculateAverageOfValues(List<NikoNikoAvgData> nikoNikoAvgDataList, int[] countOfResponse){
        nikoNikoAvgDataList.forEach(
                        nikoNikoAvgData -> {
                            switch (nikoNikoAvgData.getDayOfWeek()){
                                case MONDAY:
                                    nikoNikoAvgData.setAvg( nikoNikoAvgData.getAvg() / countOfResponse[0] );
                                    break;
                                case TUESDAY:
                                    nikoNikoAvgData.setAvg( nikoNikoAvgData.getAvg() / countOfResponse[1] );
                                    break;
                                case WEDNESDAY:
                                    nikoNikoAvgData.setAvg( nikoNikoAvgData.getAvg() / countOfResponse[2] );
                                    break;
                                case THURSDAY:
                                    nikoNikoAvgData.setAvg( nikoNikoAvgData.getAvg() / countOfResponse[3] );
                                    break;
                                case FRIDAY:
                                    nikoNikoAvgData.setAvg( nikoNikoAvgData.getAvg() / countOfResponse[4] );
                                    break;
                            }
                        }
                );
    }

    private NikoNikoAvgData getNikoNikoAvgByDayOfWeek(List<NikoNikoAvgData> nikoNikoAvgDataList, DayOfWeek dayOfWeek){
        return nikoNikoAvgDataList.stream()
                .filter( nikoNikoAvgData -> nikoNikoAvgData.getDayOfWeek().equals(dayOfWeek) )
                .findFirst()
                .get();
    }

    @Override
    public List<TwelveStepsResponseAvgDto> getTwelveStepsResponseAvgDto(List<Survey> surveys) {

        List<TwelveStepsResponseAvgDto> twelveStepsResponseAvgDtos = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            twelveStepsResponseAvgDtos.add(new TwelveStepsResponseAvgDto(
                    QuestionsConstantsTwelveSteps.QUESTIONS_CATEGORY_TWELVE_STEPS.get(i),
                    0d
            ));
        }

        surveys.forEach(survey -> {
            for (int i = 0; i < 12; i++) {
                twelveStepsResponseAvgDtos.get(i).setAverage(
                        twelveStepsResponseAvgDtos.get(i).getAverage() + twelveStepsService.getValueForAnswer(survey.getActivities().get(i).getAnswer())
                );
            }
        });

        for (int i = 0; i < 12; i++) {
            twelveStepsResponseAvgDtos.get(i).setAverage(
                    twelveStepsResponseAvgDtos.get(i).getAverage()/ getNumberOfActivitiesWithPointsDistinctOfZero(surveys, i)
            );
        }

        return twelveStepsResponseAvgDtos;
    }

    @Override
    public GeneralSummaryDto getGeneralSummaryData() {

        return null;
    }

    @Override
    public List<NotificationKudosPanelDto> getNotificationKudosPanelDto() {
        List<TeamListDTO> teamListDTOS = this.teamService.getAllTeams();
        List<NotificationKudosPanelDto> notificationKudosPanel = new ArrayList<>();


        teamListDTOS.forEach(
                teamListDTO -> {
                    notificationKudosPanel.addAll(
                            this.kudosNotificationService.getNotificationKudosLeaders( teamListDTO.getTeamLeaderDTO().getUuid() )
                    );
                }
        );

        return notificationKudosPanel;
    }

    @Override
    public List<NotificationNikoNikoPanelDto> getNotificationNikoNikoPanelDto() {
        List<TeamListDTO> teamListDTOS = this.teamService.getAllTeams();
        List<NotificationNikoNikoPanelDto> notificationNikoNikoPanel = new ArrayList<>();

        teamListDTOS.forEach(
                teamListDTO -> {
                    notificationNikoNikoPanel.addAll(
                            this.nikoNikoNotificationService.getNotificationKudosLeaders( teamListDTO.getTeamLeaderDTO().getUuid() )
                    );
                }
        );

        return notificationNikoNikoPanel;
    }

    private double getNumberOfActivitiesWithPointsDistinctOfZero(List<Survey> surveys, int activityNumber) {

        return (double) surveys.stream()
                .map(survey -> survey.getActivities().get(activityNumber))
                .filter( activity ->  activity.getAnswer() != null)
                .count();
    }

    //Si es admin --> Debe ver el de todos los equipos con las posibilidad de ver el de uno en especifico.

    private double getPercentByScale(double point, double scale) {
        return ( ((point - 1d) / scale - 1d) * 100 ) * -1;
    }

}
