package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActivityState activityState;

    private String question;
    private String answer;
    private LocalDate closeDate;

    public void closeActivity(){
        if (StringUtils.isNotBlank(this.answer)){
            this.setCloseDate(LocalDate.now());
            this.setActivityState(ActivityState.FINISHED);
        }
    }

    public void openActivity(){
        if (StringUtils.isBlank(this.answer)){
            this.setCloseDate(null);
            this.setActivityState(ActivityState.ACTIVE);
        }
    }



}
