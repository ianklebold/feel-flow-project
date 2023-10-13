package com.equipo5.feelflowapp.repository.users.regularuser;

import com.equipo5.feelflowapp.domain.users.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RegularUserRepository extends JpaRepository<RegularUser, UUID> {
    @Query(value = """
            SELECT t.uuid
            FROM team as t JOIN regular_user as ru ON t.uuid = ru.team_uuid JOIN user_model as um
            WHERE um.username = :username
            """,
            nativeQuery = true)
    String findTeamByUsername(@Param("username") String username);
}
