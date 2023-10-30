package com.equipo5.feelflowapp.repository.users.teamleader;

import com.equipo5.feelflowapp.domain.users.TeamLeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TeamLeaderRepository extends JpaRepository<TeamLeader, UUID> {

@Query(value = """
            SELECT t.uuid
            FROM team as t JOIN team_leader as tl ON t.team_leader_teamleader_persona = tl.teamleader_persona JOIN user_model as um ON tl.teamleader_persona = um.uuid
            WHERE um.username = :username
            """,
        nativeQuery = true)
    String findTeamByUsername(@Param("username") String username);
}
