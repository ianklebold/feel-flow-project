package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.dto.modules.LastSurveyDto;
import com.equipo5.feelflowapp.dto.modules.SurveyAvailableNikoNikoReponseDto;
import com.equipo5.feelflowapp.dto.modules.SurveyDto;
import com.equipo5.feelflowapp.dto.modules.SurveyTwelveStepsResponseDto;
import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.jobs.module.surveys.SurveyScheduledTask;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import com.equipo5.feelflowapp.service.notification.nikoniko.NikoNikoNotificationService;
import com.equipo5.feelflowapp.service.notification.recommendation.RecommendationService;
import com.equipo5.feelflowapp.service.survey.impl.SurveyService;
import com.equipo5.feelflowapp.service.survey.nikoniko.NikoNikoSurveyService;
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

import java.time.LocalDate;
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

    private final NikoNikoSurveyService nikoNikoSurveyService;

    private final SurveyScheduledTask surveyScheduledTask;

    private final ModuleService moduleService;

    private final NikoNikoNotificationService nikoNikoNotificationService;
    private final NotificationService notificationService;
    private final RecommendationService recommendationService;

    @Autowired
    public SurveyModuleController(@Qualifier("SurveyService") SurveyService surveyService, @Qualifier("TwelveStepsSurveyService") TwelveStepsSurveyService twelveStepsSurveyService, @Qualifier("NikoNikoSurveyServiceImpl") NikoNikoSurveyService nikoNikoSurveyService, SurveyScheduledTask surveyScheduledTask, ModuleService moduleService, NikoNikoNotificationService nikoNikoNotificationService, NotificationService notificationService,RecommendationService recommendationService) {
        this.surveyService = surveyService;
        this.twelveStepsSurveyService = twelveStepsSurveyService;
        this.nikoNikoSurveyService = nikoNikoSurveyService;
        this.surveyScheduledTask = surveyScheduledTask;
        this.moduleService = moduleService;
        this.nikoNikoNotificationService = nikoNikoNotificationService;
        this.notificationService = notificationService;
        this.recommendationService = recommendationService;
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
    @GetMapping("/filter")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<SurveyDto> getSurveys(
            @RequestParam(required = false, name = "surveyState") SurveyStateEnum surveyState,
            @RequestParam(required = false, name = "creationDate") LocalDate creationDate,
            @RequestParam(name = "moduleName") String moduleName
    ){
        return surveyService.getSurveys(surveyState, creationDate, moduleName);
    }

    @Operation(
            summary = "Get Last Survey REST API",
            description = "REST API to get the last survey"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/last")
    @SecurityRequirement(name = "Bearer Authentication")
    public LastSurveyDto getSurveysLastSurvey(){
        return surveyService.getLastSurvey();
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
    public ResponseEntity<ResponseDto> completeTwelveStepsSurvey(@Valid @RequestBody SurveyTwelveStepsResponseDto surveyResponse) throws JsonProcessingException {
        Survey surveySaved = twelveStepsSurveyService.completeSurvey(surveyResponse);

        if( SurveyStateEnum.FINISHED.equals( surveySaved.getSurveyStateEnum() ) ){
            recommendationService.sendRecommendationForTwelveSteps(surveySaved);
        }

        notificationService.sendNotificationSurvey(surveySaved);
        SurveyModule surveyModule = moduleService.closeModule(ModuleNames.TWELVE_STEPS);
        if(surveyModule != null){
            notificationService.sendNotificationModule(
                    surveyModule.getTeam().getTeamLeader(),
                    notificationService.generateBodyForCloseModule("Niko Niko"),
                    "Cierre de modulo"
            );
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
    }

    @Operation(
            summary = "Complete Surveys for Niko Niko Module REST API",
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
    @PostMapping("/niko_niko_module")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDto> completeNikoNikoSurvey(@Valid @RequestBody SurveyAvailableNikoNikoReponseDto surveyResponse){
        Survey survey = nikoNikoSurveyService.completeSurvey( surveyResponse );

        if (survey != null) {
            nikoNikoNotificationService.sendNikoNikoNote(surveyResponse.activityAvailable().descriptionFeeling(), survey);
            if(survey.getActivities().size() == 2 && survey.getActivities().get(0) != null && survey.getActivities().get(1) != null){
                if( ActivityState.FINISHED.equals(survey.getActivities().get(0).getActivityState()) && ActivityState.FINISHED.equals(survey.getActivities().get(1).getActivityState())){
                    recommendationService.sendRecommendationForNikoNiko(survey);
                }
            }
        }
        SurveyModule surveyModule = moduleService.closeModule(ModuleNames.NIKO_NIKO);
        if(surveyModule != null){
            notificationService.sendNotificationModule(
                    surveyModule.getTeam().getTeamLeader(),
                    notificationService.generateBodyForCloseModule("Niko Niko"),
                    "Cierre de modulo"
            );
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
    }

    @Operation(
            summary = "Get Survey available for Niko Niko Module REST API",
            description = "REST API to get available survey"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/niko_niko_module")
    @SecurityRequirement(name = "Bearer Authentication")
    public SurveyAvailableNikoNikoReponseDto getNikoNikoAvailableSurvey(){

        return this.nikoNikoSurveyService.getSurveyAvailable();
    }

    @Operation(
            summary = "Force creation and close of Niko Niko Surveys ",
            description = "REST API for Force creation and close of Niko Niko Surveys "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @PostMapping("/niko_niko_module/force_surveys")
    public ResponseEntity<ResponseDto> forceNikoNikoSurvey(){

        this.surveyScheduledTask.sendSurveys();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
    }


}
