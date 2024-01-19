package com.equipo5.feelflowapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Survey REST APIs",
        description = "REST APIs in Project to POST AND GET Surveys"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = SurveyModuleController.SURVEY_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class SurveyModuleController {
    public  static final String SURVEY_PATH = "/api/v1/surveys";





}
