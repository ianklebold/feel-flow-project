package com.equipo5.feelflowapp.domain.modules.twelvesteps;

import com.equipo5.feelflowapp.domain.modules.Activity;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "twelvesteps_activity")
public class ActivityTwelveSteps extends Activity {
    private String question;
    private String answer;
}
