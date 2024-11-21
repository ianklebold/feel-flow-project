package com.equipo5.feelflowapp.controller;


import com.equipo5.feelflowapp.constants.module.twelvesteps.QuestionsConstants;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Q&A REST APIs",
        description = "REST APIs in Project to GET Questions and Answers"
)
@RestController
@RequestMapping(path = QuestionsAnswersModuleController.QUESTION_AND_ANSWERS_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class QuestionsAnswersModuleController {

    public  static final String QUESTION_AND_ANSWERS_PATH = "/api/v1/questions_and_answers";

    public static final String QUESTIONS_MODULE = "/questions/{name_module}";

    public static final String ANSWERS_MODULE = "/answers/{name_module}";

    @Operation(
            summary = "Get Questions for survey",
            description = "REST API to get questions of the survey"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping(QUESTIONS_MODULE)
    @SecurityRequirement(name = "Bearer Authentication")
    public List<String> getQuestions(
            @Parameter(allowEmptyValue = false, name = "nameModule", example = "TWELVE_STEPS")
            @PathVariable(value = "name_module") String nameModule){
         return QuestionsConstants.QUESTIONS_POOL_CLASSIC_TWELVE_STEPS;
    }

    @Operation(
            summary = "Get Answers for survey",
            description = "REST API to get answers of the survey"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping(ANSWERS_MODULE)
    @SecurityRequirement(name = "Bearer Authentication")
    public List<String[]> getAnswers(
            @Parameter(allowEmptyValue = false, name = "nameModule", example = "TWELVE_STEPS")
            @PathVariable(value = "name_module") String nameModule){
        return  List.of(
                QuestionsConstants.ANSWERS_1_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_2_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_3_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_4_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_5_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_6_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_7_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_8_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_9_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_10_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_11_POOL_CLASSIC,
                QuestionsConstants.ANSWERS_12_POOL_CLASSIC
        );
    }


}
