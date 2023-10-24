package com.equipo5.feelflowapp.domain.survies;

import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    private String question;

    @Builder.Default
    @ManyToMany()
    private List<SurveyModule> surveyModule = new ArrayList<>();

    public Survey(Long id, String question, List<SurveyModule> surveyModule) {
        this.id = id;
        this.question = question;
        this.surveyModule = surveyModule;
    }

    public Survey() {}
}
