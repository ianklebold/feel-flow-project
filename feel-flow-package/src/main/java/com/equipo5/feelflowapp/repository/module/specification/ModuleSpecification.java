package com.equipo5.feelflowapp.repository.module.specification;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.modules.Module;
import jakarta.persistence.criteria.Predicate;
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

    public static Specification<Module> withPublishDate(final int mes) {

        return (root, query, criteriaBuilder) -> {
            ZoneId zonaBuenosAires = ZoneId.of("America/Argentina/Buenos_Aires");
            int anio = LocalDate.now(zonaBuenosAires).getYear();

            LocalDate primerDia = LocalDate.of(anio, mes, 1);
            LocalDate ultimoDia = primerDia.with(TemporalAdjusters.lastDayOfMonth());

            ZonedDateTime inicioDelMes = primerDia.atStartOfDay(zonaBuenosAires);
            ZonedDateTime finDelMes = ultimoDia.atTime(23, 59, 59, 999000000)
                    .atZone(zonaBuenosAires);

            Timestamp inicioTimestamp = Timestamp.from(inicioDelMes.toInstant());
            Timestamp finTimestamp = Timestamp.from(finDelMes.toInstant());

            Predicate publicacionAntesFinMes = criteriaBuilder.lessThanOrEqualTo(
                    root.get("dateAndTimeToPublish"), finTimestamp);

            Predicate cierreDespuesInicioMes = criteriaBuilder.or(
                    criteriaBuilder.isNull(root.get("dateAndTimeToClose")),
                    criteriaBuilder.greaterThanOrEqualTo(root.get("dateAndTimeToClose"), inicioTimestamp)
            );

            return criteriaBuilder.and(publicacionAntesFinMes, cierreDespuesInicioMes);
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
