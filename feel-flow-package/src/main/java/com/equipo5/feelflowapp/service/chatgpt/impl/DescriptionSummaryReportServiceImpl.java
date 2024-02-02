package com.equipo5.feelflowapp.service.chatgpt.impl;


import com.equipo5.feelflowapp.service.chatgpt.DescriptionSummaryReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DescriptionSummaryReportServiceImpl implements DescriptionSummaryReportService {


    @Override
    public String getSummaryReport(double totalSurvey){
        if (totalSurvey >= 4.5) {
            return "5. Estoy muy feliz";
        } else if (totalSurvey >= 3.5) {
            return "4.Estoy contento";
        } else if (totalSurvey >= 2.5) {
            return "3. Neutral";
        } else if (totalSurvey >= 1.5) {
            return "2.Me siento muy poco feliz";
        } else {
            return "1.No me siento feliz";
        }
    }

}
