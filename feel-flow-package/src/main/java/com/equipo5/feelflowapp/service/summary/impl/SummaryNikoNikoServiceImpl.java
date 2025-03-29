package com.equipo5.feelflowapp.service.summary.impl;

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
import java.util.UUID;

@AllArgsConstructor
@Service
public class SummaryNikoNikoServiceImpl implements SummaryNikoNikoService {

    private final TeamRepository teamRepository;
    private final ModuleService moduleService;

    @Override
    public List<SummaryNikoNikoDto> getSummary(UUID idTeam, Integer numberOfMouth) {
        List<SummaryNikoNikoDto> summaries = new ArrayList<>();
        teamRepository.findById(idTeam).ifPresent(team -> {
            LocalDate date;
            if(numberOfMouth == null){
                 date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);

            }else{
                 date = LocalDate.of(LocalDate.now().getYear(), numberOfMouth, 1);

            }
            List<SurveyModule> surveyModules = moduleService.getSurveyModuleByPublishDate(date, ModuleNames.NIKO_NIKO.toString(),team);

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
        });

        return summaries;
    }

    private Integer getCloseDate(Activity activity) {
        if(activity.getCloseDate() == null){
            return null;
        }
        return activity.getCloseDate().getDayOfMonth();
    }


}
