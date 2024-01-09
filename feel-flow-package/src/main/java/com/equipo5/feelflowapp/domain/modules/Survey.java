package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.users.RegularUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Survey{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SurveyStateEnum surveyStateEnum;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();

    @ManyToOne
    private SurveyModule surveyModule;

    @ManyToOne
    private RegularUser regularUser;
}
