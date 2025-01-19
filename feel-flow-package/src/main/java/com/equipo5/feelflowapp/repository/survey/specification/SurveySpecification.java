package com.equipo5.feelflowapp.repository.survey.specification;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import org.springframework.data.jpa.domain.Specification;


public class SurveySpecification {
    public static Specification<Survey> withSurveyState(SurveyStateEnum surveyStateEnum) {
        return (root, query, criteriaBuilder) -> {
            if (surveyStateEnum == null) {
                return criteriaBuilder.conjunction(); // No filtrar si es null
            }
            return criteriaBuilder.equal(root.get("surveyStateEnum"), surveyStateEnum);
        };
    }

    public static Specification<Survey> withRegularUser(RegularUser regularUser) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regularUser"), regularUser);
    }

    public static Specification<Survey> withModule(SurveyModule module) {
        return (root, query, criteriaBuilder) -> {
            if (module == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("surveyModule"), module);
        };
    }

}
