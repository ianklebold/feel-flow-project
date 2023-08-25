package com.equipo5.feelflowapp.service.locator;

import com.equipo5.feelflowapp.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocatorService {
    JpaRepository<? extends User, UUID>
}
