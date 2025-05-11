package com.equipo5.feelflowapp.service.notification.recommendation;


import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.dto.notifications.RecommendationDto;

import java.util.List;


public interface RecommendationService {
    void sendRecommendationForTwelveSteps(Survey survey);
    void sendRecommendationForNikoNiko(Survey survey);
    void sendRecommendationForKudos(KudosModule kudosModule);
    List<RecommendationDto> getRecommendations();
}
