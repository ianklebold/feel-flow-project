package com.equipo5.feelflowapp.service.summary.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.dto.summary.SummaryNikoNikoDto;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.summary.SummaryNikoNikoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SummaryNikoNikoServiceImpl implements SummaryNikoNikoService {

    private final TeamRepository teamRepository;
    private final ModuleService moduleService;

    @Override
    public List<SummaryNikoNikoDto> getSummary(UUID idTeam, Integer numberOfMonth) {
        List<SummaryNikoNikoDto> summaries = new ArrayList<>();
        if(numberOfMonth == null){
            numberOfMonth = LocalDate.now().getMonth().getValue();
        }
        Optional<Team> team = teamRepository.findById(idTeam);
        if(team.isPresent()){
            List<SurveyModule> surveyModules = moduleService.getSurveyModuleByPublishDate(numberOfMonth, ModuleNames.NIKO_NIKO.toString(),team.get());

            if (!surveyModules.isEmpty()) {
                SurveyModule surveyModule = surveyModules.get(0);
                if(surveyModule.getSurveys() != null){
                    surveyModule.getSurveys().forEach(survey -> {
                        summaries.add(new SummaryNikoNikoDto(
                                survey.getRegularUser().getName(),
                                survey.getRegularUser().getSurname(),
                                getCloseDate(survey.getActivities().get(0)),
                                survey.getActivities().get(0).getCloseDate(),
                                survey.getActivities().get(0).getAnswer(),
                                survey.getActivities().get(1).getAnswer()
                        ));
                    });
                }
            }
        }
        return summaries;
    }

    private Integer getCloseDate(Activity activity) {
        if(activity.getCloseDate() == null){
            return null;
        }
        return activity.getCloseDate().getDayOfMonth();
    }


}
