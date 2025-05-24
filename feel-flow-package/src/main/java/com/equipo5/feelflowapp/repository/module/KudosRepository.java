package com.equipo5.feelflowapp.repository.module;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KudosRepository extends JpaRepository<KudosModule, Long> {
    Optional<KudosModule> findByModuleStateAndTeam(ModuleState moduleState, Team team);
    List<KudosModule> findAllByModuleStateAndTeam(ModuleState moduleState, Team team);
}
