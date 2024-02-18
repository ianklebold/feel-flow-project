package com.equipo5.feelflowapp.mappers.modules.custom.impl;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import com.equipo5.feelflowapp.dto.modules.ModuleListDto;
import com.equipo5.feelflowapp.dto.modules.ResumeModuleDto;
import com.equipo5.feelflowapp.mappers.modules.SurveyListMapper;
import com.equipo5.feelflowapp.mappers.modules.custom.ModuleTwelveStepsMapperCustom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ModuleTwelveStepsMapperCustomImpl implements ModuleTwelveStepsMapperCustom {

    private final SurveyListMapper surveyListMapper;
    @Override
    public void mapModuleToTwelveStepsModule(List<ModuleListDto> modulesDtos, List<Module> modules) {
        setSurveysToTwelveStepsModuleDto(modulesDtos,modules);
        setIsReadyToCloseTwelveStepsModule(modulesDtos,modules);
        setResumeModuleToTwelveStepsModuleDto(modulesDtos);
    }

    private void setResumeModuleToTwelveStepsModuleDto(List<ModuleListDto> modulesDtos){

        modulesDtos.forEach(
                module -> {

                    int totalOfSurveys = module.getSurveyList().size();
                    int totalOfSurveysReplied = module.getSurveyList()
                            .stream()
                            .filter(survey -> survey.surveyState().equals(SurveyStateEnum.FINISHED) || survey.surveyState().equals(SurveyStateEnum.CLOSED)
                            ).toList().size();

                    double percentOfSurveysReplied =  ((double)totalOfSurveysReplied / (double)totalOfSurveys) * 100;

                        module.setResumeModuleDto(
                                new ResumeModuleDto(totalOfSurveys,totalOfSurveysReplied,percentOfSurveysReplied)
                        );
                }
        );

    }

    private void setSurveysToTwelveStepsModuleDto(List<ModuleListDto> modulesDtos,List<Module> modules){
        mapListOfModulesToTwelveStepsModules(modules)
                .forEach( module ->
                        {
                            var moduleDto = modulesDtos
                                    .stream()
                                    .filter(modulesDto-> modulesDto.getId().equals(module.getId()))
                                    .findFirst();

                            if (moduleDto.isPresent()){

                                moduleDto.get().setSurveyList(
                                        module.getSurveys()
                                                .stream()
                                                .map(surveyListMapper::surveyToSurveyListDto)
                                                .toList()
                                );
                            }
                        }
                );
    }

    private void setIsReadyToCloseTwelveStepsModule(List<ModuleListDto> modulesDtos,List<Module> modules){
        List<TwelveStepsModule> twelveStepsModuleList = mapListOfModulesToTwelveStepsModules(modules);

        modules.stream()
                .filter( module ->(!module.getModuleState().equals(ModuleState.FINISHED)))
                .map( module -> module.getId())
                .forEach( moduleId -> {
                            var moduleDto = modulesDtos.stream().filter(module -> Objects.equals(module.getId(), moduleId)).findFirst();

                            if (moduleDto.isPresent()){
                                moduleDto.get().setEnableToClose(isReadyToCloseModule(twelveStepsModuleList,moduleDto.get().getId()));
                            }
                        }
                );
    }

    private List<TwelveStepsModule> mapListOfModulesToTwelveStepsModules(List<Module> modules){
        return modules.stream()
                .filter(module -> module.getName().equals(ModuleNames.TWELVE_STEPS.toString()))
                .map(TwelveStepsModule.class::cast)
                .toList();
    }

    private boolean isReadyToCloseModule(List<TwelveStepsModule> stepsModuleList, Long twelveStepsModuleId) {
        return stepsModuleList.stream()
                .filter(module -> module.getId().equals(twelveStepsModuleId))
                .findFirst()
                .get()
                .getSurveys()
                .stream()
                .noneMatch(survey -> survey.getSurveyStateEnum().equals(SurveyStateEnum.ACTIVE));
    }
}
