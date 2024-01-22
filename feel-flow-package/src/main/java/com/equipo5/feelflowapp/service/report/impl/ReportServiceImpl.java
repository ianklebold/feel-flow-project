package com.equipo5.feelflowapp.service.report.impl;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.report.Report;
import com.equipo5.feelflowapp.repository.report.ReportRepository;
import com.equipo5.feelflowapp.service.report.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final String OPTION_ONE = "UNO";

    private final String OPTION_TWO = "DOS";

    private final String OPTION_THREE = "TRES";

    private final String OPTION_FOUR = "CUATRO";

    private final String OPTION_FIVE = "CINCO";

    @Override
    public Report createReportToTwelveSteps(Survey survey) {

        int[] values = {0,0,0,0,0};

        survey.getActivities().forEach(activity -> sumValue(activity.getAnswer(),values));

        Report report = new Report();
        report.setTotalSurvey(values);

        return reportRepository.save(report);
    }

    private void sumValue(final String answer,int[] values){

        switch (answer) {
            case OPTION_ONE -> values[0] = values[0] + 1;
            case OPTION_TWO -> values[1] = values[1] + 1;
            case OPTION_THREE -> values[2] = values[2] + 1;
            case OPTION_FOUR -> values[3] = values[3] + 1;
            case OPTION_FIVE -> values[4] = values[4] + 1;
            default -> values[0] = values[0] + 0;
        }

    }


}
