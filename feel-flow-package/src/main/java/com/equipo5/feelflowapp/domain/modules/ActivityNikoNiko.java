package com.equipo5.feelflowapp.domain.modules;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.time.DayOfWeek;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "nikonikoactivity_activity")
public class ActivityNikoNiko extends Activity{

    private String descriptionFeeling;
    private DayOfWeek dayOfWeek;


}


