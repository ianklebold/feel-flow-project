package com.equipo5.feelflowapp.repository.module;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ModuleNikoNikoRepository extends JpaRepository<NikoNikoModule, Long> {

    List<NikoNikoModule> findAllByModuleStateAndTeam(ModuleState state, Team team);
}
