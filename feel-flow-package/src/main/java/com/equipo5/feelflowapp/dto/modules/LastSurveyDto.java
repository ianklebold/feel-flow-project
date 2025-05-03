package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.dto.images.ImagesDto;

import java.util.List;

public record LastSurveyDto(
        SurveyDto surveyDto,
        ImagesDto teamLeadImage,
        List<TwelveStepsResponseAvgDto> surveyResult) {}