package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import jakarta.persistence.*;
import lombok.*;

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
}
