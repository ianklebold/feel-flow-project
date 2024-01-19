package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.dto.modules.SurveyDto;
import com.equipo5.feelflowapp.service.survey.SurveyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Survey REST APIs",
        description = "REST APIs in Project to POST AND GET Surveys"
)
@Slf4j
@RestController
@RequestMapping(path = SurveyModuleController.SURVEY_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class SurveyModuleController {
    public  static final String SURVEY_PATH = "/api/v1/surveys";

    private final SurveyService surveyService;

    @Autowired
    public SurveyModuleController(@Qualifier("SurveyService") SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    //Endpoint para listar todas las encuestas de current user.
    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public List<SurveyDto> getSurveys(){
        return surveyService.getSurveys();
    }

}
