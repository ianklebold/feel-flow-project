package com.equipo5.feelflowapp.repository.badge;

import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
