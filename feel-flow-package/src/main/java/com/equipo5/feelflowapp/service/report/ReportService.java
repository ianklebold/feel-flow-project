package com.equipo5.feelflowapp.service.report;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.report.Report;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ReportService {
    Report createReportToTwelveSteps(Survey survey) throws JsonProcessingException;
}
