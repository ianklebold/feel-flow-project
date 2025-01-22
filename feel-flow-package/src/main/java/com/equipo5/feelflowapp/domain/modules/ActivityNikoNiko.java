package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "nikonikoactivity_activity")
public class ActivityNikoNiko extends Activity{

    private String descriptionFeeling;

    @Builder
    public ActivityNikoNiko(Long id, ActivityState activityState, String question, String answer, LocalDate closeDate, String descriptionFeeling) {
        super(id, activityState, question, answer, closeDate);
        this.descriptionFeeling = descriptionFeeling;
    }
}


