package com.equipo5.feelflowapp.service.notification.recommendation.impl;

import com.equipo5.feelflowapp.constants.module.kudos.SuggestionsConstantsKudos;
import com.equipo5.feelflowapp.constants.module.nikoniko.ResponseConstantsNikoNiko;
import com.equipo5.feelflowapp.constants.module.nikoniko.SuggestionsConstantsNikoNiko;
import com.equipo5.feelflowapp.constants.module.twelvesteps.QuestionsConstantsTwelveSteps;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import com.equipo5.feelflowapp.domain.notifications.Recommendation;
import com.equipo5.feelflowapp.domain.notifications.Suggestion;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.notifications.RecommendationDto;
import com.equipo5.feelflowapp.mappers.notifications.RecommendationMapper;
import com.equipo5.feelflowapp.repository.module.KudosRepository;
import com.equipo5.feelflowapp.repository.module.ModuleNikoNikoRepository;
import com.equipo5.feelflowapp.repository.module.ModuleTwelveStepsRepository;
import com.equipo5.feelflowapp.repository.notifications.recommendation.RecommendationRepository;
import com.equipo5.feelflowapp.repository.notifications.recommendation.SuggestionRepository;
import com.equipo5.feelflowapp.repository.users.teamleader.TeamLeaderRepository;
import com.equipo5.feelflowapp.service.module.kudos.KudosService;
import com.equipo5.feelflowapp.service.notification.recommendation.RecommendationService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final SuggestionRepository suggestionRepository;

    private final TeamLeaderRepository teamLeaderRepository;

    private final KudosService kudosService;

    private final UserService userService;

    private final RecommendationMapper recommendationMapper;

    private final KudosRepository kudosRepository;

    private final ModuleTwelveStepsRepository moduleTwelveStepsRepository;

    private final ModuleNikoNikoRepository nikoNikoRepository;

    @Override
    public void sendRecommendationForTwelveSteps(Survey survey) {

        List<Activity> activities = survey.getActivities();
        String user = survey.getRegularUser().getName() + " " + survey.getRegularUser().getSurname();
        String teamName = survey.getRegularUser().getTeam().getName();

        for (int i = 0; i < QuestionsConstantsTwelveSteps.QUESTIONS_POOL_CLASSIC_TWELVE_STEPS.size(); i++) {

            String question = QuestionsConstantsTwelveSteps.QUESTIONS_POOL_CLASSIC_TWELVE_STEPS.get(i);
            String answer = activities.stream().filter( activity -> activity.getQuestion().equals(question)
            ).findFirst().get().getAnswer();



            if (answer.startsWith("4") || answer.startsWith("5")) {
                String category = QuestionsConstantsTwelveSteps.QUESTIONS_CATEGORY_TWELVE_STEPS.get(i);

                String message = QuestionsConstantsTwelveSteps.RECOMMENDATION.get(category)
                        .replace("@user", user)
                        .replace("@equipo", teamName);

                List<Suggestion> suggestions = QuestionsConstantsTwelveSteps.SUGGESTION.get(category)
                        .stream()
                        .map( suggestion -> Suggestion.builder().description( suggestion ).build() )
                        .toList();

                suggestions = suggestionRepository.saveAll(suggestions);

                Recommendation recommendation = Recommendation.builder()
                        .title("Recomendacion 12 pasos de la felicidad")
                        .notificationOwner(survey.getSurveyModule().getTeam().getTeamLeader())
                        .body(message)
                        .suggestion(suggestions)
                        .wasRead(false)
                        .wasSeen(false)
                        .notificationTypeEnum(NotificationTypeEnum.SUGGESTIONS)
                        .createdAt(LocalDateTime.now())
                        .build();

                recommendationRepository.save(recommendation);

            }


        }

    }

    @Override
    public void sendRecommendationForNikoNiko(Survey survey) {
        List<Activity> activities = survey.getActivities();
        String message = SuggestionsConstantsNikoNiko.RECOMMENDATION.replace("@user", survey.getRegularUser().getName() + " " + survey.getRegularUser().getSurname());
        if( !activities.get(0).getAnswer().isEmpty() && !activities.get(1).getAnswer().isEmpty() ){

            if( activities.get(0).getAnswer().equals(ResponseConstantsNikoNiko.ANSWERS_PERSONAL_POOL_NIKO_NIKO) && activities.get(1).getAnswer().equals(ResponseConstantsNikoNiko.ANSWERS_PERSONAL_POOL_NIKO_NIKO) ){

                List<Suggestion> suggestions = suggestionRepository.saveAll(
                        SuggestionsConstantsNikoNiko.SUGGESTIONS
                                .stream()
                                .map( suggestion -> Suggestion.builder().description(suggestion).build() )
                                .toList()
                );

                Recommendation recommendation = Recommendation.builder()
                        .title("Recomendacion Niko Niko")
                        .notificationOwner(survey.getSurveyModule().getTeam().getTeamLeader())
                        .body(message)
                        .suggestion(suggestions)
                        .wasRead(false)
                        .wasSeen(false)
                        .notificationTypeEnum(NotificationTypeEnum.SUGGESTIONS)
                        .createdAt(LocalDateTime.now())
                        .build();

                recommendationRepository.save(recommendation);
            }
        }


    }

    @Override
    public void sendRecommendationForKudos(KudosModule kudosModule) {
        TeamLeader teamLeader = kudosModule.getTeam().getTeamLeader();
        List<RegularUser> regularUsers = kudosModule.getTeam().getRegularUsers();
        List<RegularUser> usersAwardedKudos = kudosService.usersAwardedByModule(kudosModule);
        List<RegularUser> usersWithoutKudos = findUsersNotAwarded( regularUsers , usersAwardedKudos );

        if(!usersWithoutKudos.isEmpty()){
            List<Suggestion> suggestions = suggestionRepository.saveAll(
                    SuggestionsConstantsKudos.SUGGESTIONS
                            .stream()
                            .map( suggestion -> Suggestion.builder().description(suggestion).build() )
                            .toList()
            );


            usersWithoutKudos.forEach(
                    user -> recommendationRepository.save(
                            Recommendation.builder()
                                    .title("Recomendacion Kudos")
                                    .body(SuggestionsConstantsKudos.RECOMMENDATION.replace("@user", user.getName() + " " + user.getSurname()))
                                    .createdAt(LocalDateTime.now())
                                    .wasRead(false)
                                    .wasSeen(false)
                                    .suggestion(suggestions)
                                    .notificationTypeEnum(NotificationTypeEnum.SUGGESTIONS)
                                    .notificationOwner(teamLeader)
                                    .build()
                    )
            );
        }
    }

    @Override
    public List<RecommendationDto> getRecommendations() {
        String currentUserName = this.userService.getUsernameByCurrentUser();
        Optional<TeamLeader> teamLeader = this.teamLeaderRepository.findByUsername(currentUserName);

        if(teamLeader.isPresent()){
            List<Recommendation> recommendations = recommendationRepository
                    .findAllByNotificationOwnerAndNotificationTypeEnumOrderByCreatedAtDesc(
                            teamLeader.get(),
                            NotificationTypeEnum.SUGGESTIONS
                            );

            return recommendations.stream()
                    .map(recommendationMapper::reccomendationToRecommendationDto)
                    .toList();
        }
        return List.of();
    }

    @Override
    public void sendRecommendation() {
        String currentUserName = this.userService.getUsernameByCurrentUser();
        Optional<TeamLeader> teamLeader = this.teamLeaderRepository.findByUsername(currentUserName);

        if(teamLeader.isPresent()){
            List<TwelveStepsModule> twelveStepsModuleList = this.moduleTwelveStepsRepository.findAllByModuleStateAndTeam(ModuleState.FINISHED,teamLeader.get().getTeam());
            List<NikoNikoModule> nikoNikoModuleList = this.nikoNikoRepository.findAllByModuleStateAndTeam(ModuleState.FINISHED, teamLeader.get().getTeam());
            List<KudosModule> kudosModules = this.kudosRepository.findAllByModuleStateAndTeam(ModuleState.FINISHED, teamLeader.get().getTeam());


            twelveStepsModuleList.stream()
                    .flatMap( module -> module.getSurveys().stream() )
                    .forEach(this::sendRecommendationForTwelveSteps);

            nikoNikoModuleList.stream()
                    .flatMap( module -> module.getSurveys().stream() )
                    .forEach(this::sendRecommendationForNikoNiko);

            kudosModules.forEach(this::sendRecommendationForKudos);
        }
    }

    public List<RegularUser> findUsersNotAwarded(
            List<RegularUser> regularUsers,
            List<RegularUser> usersAwardedKudos
    ) {
        Set<UUID> awardedIds = usersAwardedKudos.stream()
                .map(RegularUser::getUuid)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return regularUsers.stream()
                .filter(user -> user.getUuid() != null && !awardedIds.contains(user.getUuid()))
                .collect(Collectors.toList());
    }


}
