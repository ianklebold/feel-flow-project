package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.dto.modules.SurveyDto;
import com.equipo5.feelflowapp.dto.modules.SurveyResponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.service.survey.SurveyService;
import com.equipo5.feelflowapp.service.survey.twelvesteps.TwelveStepsSurveyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Survey REST APIs",
        description = "REST APIs in Project to POST AND GET Surveys"
)
@Slf4j
@RestController
@RequestMapping(path = SurveyModuleController.SURVEY_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class SurveyModuleController {
    public  static final String SURVEY_PATH = "/api/v1/surveys";

    private final SurveyService surveyService;

    private final TwelveStepsSurveyService twelveStepsSurveyService;

    @Autowired
    public SurveyModuleController(@Qualifier("SurveyService") SurveyService surveyService, @Qualifier("TwelveStepsSurveyService") TwelveStepsSurveyService twelveStepsSurveyService) {
        this.surveyService = surveyService;
        this.twelveStepsSurveyService = twelveStepsSurveyService;
    }

    //Endpoint para listar todas las encuestas de current user.
    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public List<SurveyDto> getSurveys(){
        return surveyService.getSurveys();
    }

    //Endpoint que permita contestar la encuesta activa de 12 pasos hacia la felicidad
    //TODO Agregar @VALID!!!!!!!!!!!!!!!
    @PostMapping("/twelve_steps_module")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDto> completeSurvey(@Valid @RequestBody SurveyResponseDto surveyResponse){
            twelveStepsSurveyService.completeSurvey(surveyResponse);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
    }
}
