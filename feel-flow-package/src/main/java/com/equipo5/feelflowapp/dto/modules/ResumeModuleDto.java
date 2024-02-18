package com.equipo5.feelflowapp.dto.modules;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ResumeModuleDto",
        description = "Schema to hold resume module information"
)
public record ResumeModuleDto(
        @Schema(description = "Total of surveys", example = "10") int totalOfSurveys,
        @Schema(description = "Total of surveys replied", example = "5") int totalOfSurveysReplied,
        @Schema(description = "Percent of surveys replied", example = "50.0") double percentOfSurveysReplied
) {

    public ResumeModuleDto(int totalOfSurveys,int totalOfSurveysReplied, double percentOfSurveysReplied){
        this.totalOfSurveys = totalOfSurveys;
        this.totalOfSurveysReplied = totalOfSurveysReplied;
        if (totalOfSurveys <= 0){
            this.percentOfSurveysReplied = 0d;
        }else {
            this.percentOfSurveysReplied = percentOfSurveysReplied;
        }
    }

}
