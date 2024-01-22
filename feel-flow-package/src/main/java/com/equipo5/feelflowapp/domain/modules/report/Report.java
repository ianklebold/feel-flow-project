package com.equipo5.feelflowapp.domain.modules.report;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalSurvey;

    private String descriptionSummary;

    public void setTotalSurvey(int[] values) {

        this.totalSurvey = (double) (values[0] + values[1] + values[2] + values[3] + values[4])/12;
    }
}
