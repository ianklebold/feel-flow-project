package com.equipo5.feelflowapp.domain.modules;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;



@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "twelvesteps_module")
public class TwelveStepsModule extends SurveyModule{

}
