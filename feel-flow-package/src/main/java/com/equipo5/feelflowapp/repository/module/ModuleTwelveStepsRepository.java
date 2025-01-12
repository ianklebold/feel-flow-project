package com.equipo5.feelflowapp.repository.module;

import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModuleTwelveStepsRepository extends JpaRepository<TwelveStepsModule,Long> {
    @Query(value = """
            SELECT t.uuid
            FROM regular_user ru2 JOIN user_model um ON ru2.regularuser_persona = um.uuid JOIN team t ON t.uuid = ru2.team_uuid
            WHERE um.username = :username
            """,
            nativeQuery = true)
    String findTeamByUsername(@Param("username") String username);
}
