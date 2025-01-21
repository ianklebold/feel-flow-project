package com.equipo5.feelflowapp.repository.module;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Long>, JpaSpecificationExecutor<Module> {

    List<Module> findModulesByTeamOrderByIdDescCreationDateDesc(Team team);

    Optional<Module> findModuleByTeamAndModuleStateAndName(Team team, ModuleState state, String moduleNames);

}
