package com.equipo5.feelflowapp.repository.team;

import com.equipo5.feelflowapp.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    @Query(value = """      
            SELECT t.*
            FROM (
            	SELECT ru.team_uuid FROM regular_user ru WHERE ru.regularuser_persona = :uuid
            	UNION
            	SELECT tl_table.team_uuid FROM (
            		SELECT t.uuid as team_uuid
            		FROM team_leader tl INNER JOIN team t ON tl.teamleader_persona = t.team_leader_teamleader_persona
            		WHERE tl.teamleader_persona = :uuid
            		) as tl_table
            ) as team_table INNER JOIN team t ON t.uuid = team_table.team_uuid;
            """,
            nativeQuery = true
    )
    Team findTeamByUUIDUser(@Param("uuid") String uuid);
}
