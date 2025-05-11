package com.equipo5.feelflowapp.mappers.notifications;

import com.equipo5.feelflowapp.domain.notifications.Recommendation;
import com.equipo5.feelflowapp.domain.notifications.Suggestion;
import com.equipo5.feelflowapp.dto.notifications.RecommendationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


import java.util.List;

@Mapper
public interface RecommendationMapper {

    @Mapping(source = "title" , target = "title")
    @Mapping(source = "body" , target = "body")
    @Mapping(source = "createdAt" , target = "createdAt")
    @Mapping(source = "suggestion" , target = "suggestions", qualifiedByName = "suggestionsMapper")
    RecommendationDto reccomendationToRecommendationDto(Recommendation recommendation);

    @Named("suggestionsMapper")
    public static List<String> suggestionsMapper(List<Suggestion> suggestion) {
        return suggestion.stream().map(Suggestion::getDescription).toList();
    }
}
