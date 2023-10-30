package com.equipo5.feelflowapp.repository.users.regularuser;

import com.equipo5.feelflowapp.domain.users.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegularUserRepository extends JpaRepository<RegularUser, UUID> {
}
