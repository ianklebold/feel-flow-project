package com.equipo5.feelflowapp.repository.module;

import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KudosRepository extends JpaRepository<KudosModule, Long> {
}
