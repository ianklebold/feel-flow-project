package com.equipo5.feelflowapp.service.report.impl;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.report.Report;
import com.equipo5.feelflowapp.repository.report.ReportRepository;
import com.equipo5.feelflowapp.service.chatgpt.DescriptionSummaryReportService;
import com.equipo5.feelflowapp.service.report.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.equipo5.feelflowapp.constants.module.twelvesteps.response.ResponseQuestionsConstants.*;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final DescriptionSummaryReportService descriptionSummaryReportService;

    @Override
    public Report createReportToTwelveSteps(Survey survey){

        int[] values = {0,0,0,0,0};

        survey.getActivities().forEach(activity -> sumValue(activity.getAnswer(),values));

        Report report = new Report();
        report.setTotalSurvey(values);
        report.setDescriptionSummary(descriptionSummaryReportService.getSummaryReport(report.getTotalSurvey()));


        return reportRepository.save(report);
    }

    private void sumValue(final String answer,int[] values){

        switch (answer) {
            case OPTION_ONE -> values[0] = values[0] + 1;
            case OPTION_TWO -> values[1] = values[1] + 2;
            case OPTION_THREE -> values[2] = values[2] + 3;
            case OPTION_FOUR -> values[3] = values[3] + 4;
            case OPTION_FIVE -> values[4] = values[4] + 5;
            default -> values[0] = values[0] + 0;
        }

    }


}
