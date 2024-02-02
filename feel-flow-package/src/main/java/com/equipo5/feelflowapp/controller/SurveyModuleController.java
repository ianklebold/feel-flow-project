package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.dto.modules.SurveyDto;
import com.equipo5.feelflowapp.dto.modules.SurveyTwelveStepsResponseDto;
import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.service.survey.SurveyService;
import com.equipo5.feelflowapp.service.survey.twelvesteps.TwelveStepsSurveyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get Surveys active REST API",
            description = "REST API to get surveys active"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public List<SurveyDto> getSurveys(){
        return surveyService.getSurveys();
    }

    @Operation(
            summary = "Complete Surveys for Twelve Steps Module REST API",
            description = "REST API to complete survey"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status BAD REQUEST",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/twelve_steps_module")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDto> completeSurvey(@Valid @RequestBody SurveyTwelveStepsResponseDto surveyResponse) throws JsonProcessingException {
            twelveStepsSurveyService.completeSurvey(surveyResponse);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
    }
}
