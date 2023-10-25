package com.equipo5.feelflowapp.repository.modules;

import com.equipo5.feelflowapp.domain.modules.TwelveStepsModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TwelveStepModuleRepository extends JpaRepository<TwelveStepsModule, UUID> {

    @Query(value = """
                   SELECT *
                   FROM  twelve_steps_module ts INNER JOIN survey_module sm INNER JOIN module_model mm
                   WHERE mm.team_uuid = :idTeam and mm.module_state = :state
                   """,nativeQuery = true)
    List<TwelveStepsModule> findTwelveModulesByTeamAndState(@Param("idTeam") String idTeam, @Param("state") String state);

}
