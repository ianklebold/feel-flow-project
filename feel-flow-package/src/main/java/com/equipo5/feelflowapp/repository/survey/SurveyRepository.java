package com.equipo5.feelflowapp.repository.survey;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey,Long> {

    List<Survey> getAllByRegularUserAndSurveyStateEnum(RegularUser regularUser, SurveyStateEnum surveyState);

}
