package com.equipo5.feelflowapp.service.survey.impl;


import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.SurveyDto;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.mappers.modules.SurveyMapper;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.service.survey.SurveyService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service("SurveyService")
@AllArgsConstructor
public class SurveyServiceImpl implements SurveyService{

    protected final SurveyRepository surveyRepository;

    protected final UserRepository userRepository;

    protected final UserService userService;

    protected final SurveyMapper surveyMapper;

    @Override
    public List<SurveyDto> getSurveys() {
        var username = userService.getUsernameByCurrentUser();

        var regularUser = userRepository.findByUsername(username);

        if (regularUser.isPresent()){
            List<Survey> surveys = surveyRepository.getAllByRegularUserAndSurveyStateEnum((RegularUser) regularUser.get(), SurveyStateEnum.ACTIVE);

            return surveys.stream()
                    .map(surveyMapper::surveyToSurveyDto)
                    .toList();


        }
        return Collections.emptyList();
    }

    @Override
    public Optional<SurveyDto> getSurveyActiveByModuleName(ModuleNames moduleNames) {
            return getSurveys().stream()
                    .filter(survey -> survey.module().filterTwelveStepsModules(moduleNames))
                    .findFirst();
    }

    @Override
    public Survey getSurveyById(Long id) {
        Optional<Survey> survey = surveyRepository.findById(id);
        if (survey.isEmpty()){
            throw new NotFoundException("No se encontro la encuesta disponible para el modulo");
        }
        return survey.get();
    }
}
