package com.equipo5.feelflowapp.domain.answers;

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
@PrimaryKeyJoinColumn(name = "twelvesteps_answer")
public class TwelveStepsAnswer extends Answer{

    private int answer;

    private String howImprove;
}
