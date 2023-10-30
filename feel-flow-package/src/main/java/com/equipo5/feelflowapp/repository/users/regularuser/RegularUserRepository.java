package com.equipo5.feelflowapp.repository.users.regularuser;

import com.equipo5.feelflowapp.domain.users.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RegularUserRepository extends JpaRepository<RegularUser, UUID> {
    @Query(value = """
            SELECT t.uuid
            FROM regular_user ru2 JOIN user_model um ON ru2.regularuser_persona = um.uuid JOIN team t ON t.uuid = ru2.team_uuid
            WHERE um.username = :username
            """,
            nativeQuery = true)
    String findTeamByUsername(@Param("username") String username);
}
