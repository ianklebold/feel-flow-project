package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Survey{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SurveyStateEnum surveyStateEnum;

    @ManyToOne
    private RegularUser regularUser;

    @ManyToOne
    private SurveyModule surveyModule;

    private LocalDate closeDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();

    public void setRegularUser(RegularUser regularUser) {
        this.regularUser = regularUser;
        regularUser.getSurveys().add(this);
    }
}
