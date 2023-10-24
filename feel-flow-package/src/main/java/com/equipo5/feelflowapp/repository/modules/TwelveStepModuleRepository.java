package com.equipo5.feelflowapp.repository.modules;

import com.equipo5.feelflowapp.domain.modules.TwelveStepsModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TwelveStepModuleRepository extends JpaRepository<TwelveStepsModule, UUID> {
}
