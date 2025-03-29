package com.equipo5.feelflowapp.repository.module.specification;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.modules.Module;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;

public class ModuleSpecification {

    public static Specification<Module> withCreationDate(final LocalDate creationDate) {
        return (root, query, criteriaBuilder) -> {
            if(creationDate != null) {
                return criteriaBuilder.lessThan(root.get("creationDate"), creationDate);
            }else {
                return criteriaBuilder.lessThan(root.get("creationDate"), LocalDate.now());
            }
        };
    }

    public static Specification<Module> withPublishDate(final LocalDate publishDate) {

        return (root, query, criteriaBuilder) -> {
                ZoneId zonaBuenosAires = ZoneId.of("America/Argentina/Buenos_Aires");
                ZonedDateTime zonedDate = publishDate.atStartOfDay(zonaBuenosAires);

                LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), publishDate.getMonth(), 1)
                        .with(TemporalAdjusters.lastDayOfMonth());
                ZonedDateTime zonedEndDate = endDate.atStartOfDay(zonaBuenosAires);

            return criteriaBuilder.between(root.get("dateAndTimeToPublish"),
                        Timestamp.from(zonedDate.toInstant()),
                        Timestamp.from(zonedEndDate.toInstant()));
        };
    }

    public static Specification<Module> withName(final String moduleName) {
        return (root, query, criteriaBuilder) -> {
            if(moduleName == null || moduleName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }else {
                return criteriaBuilder.equal(root.get("name"), moduleName);
            }
        };

    }

    public static Specification<Module> withTeam(final Team team) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("team"), team);

    }

}
