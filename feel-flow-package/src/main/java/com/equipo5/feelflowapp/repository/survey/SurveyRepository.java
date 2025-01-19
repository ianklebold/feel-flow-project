package com.equipo5.feelflowapp.repository.survey;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey,Long>, JpaSpecificationExecutor<Survey> {

    List<Survey> getAllByRegularUserAndSurveyStateEnum(RegularUser regularUser, SurveyStateEnum surveyState);

    Survey getSurveyByRegularUserAndSurveyModule(RegularUser regularUser, SurveyModule surveyModule);

    Survey getSurveyByRegularUserAndSurveyModuleAndSurveyStateEnum(RegularUser regularUser, SurveyModule surveyModule, SurveyStateEnum surveyState);
}
