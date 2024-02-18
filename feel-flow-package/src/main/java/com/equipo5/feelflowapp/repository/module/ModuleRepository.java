package com.equipo5.feelflowapp.repository.module;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module,Long> {

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.name like :name AND m.team.uuid = :idTeam " +
            "ORDER BY m.creationDate ASC")
    List<Module> findAllModulesByNameWithCreationDateOrder(@Param("name")String name,@Param("idTeam") UUID idTeam);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.name like :name AND m.team.uuid = :idTeam ")
    List<Module> findAllModulesByNameWithoutOrder(@Param("name")String name,@Param("idTeam") UUID idTeam);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.moduleState = :state AND m.team.uuid = :idTeam ")
    List<Module> findAllModulesByStateWithoutOrder(@Param("state")ModuleState state,@Param("idTeam") UUID idTeam);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE  m.moduleState = :state AND m.team.uuid = :idTeam " +
            "ORDER BY m.creationDate ASC")
    List<Module> findAllModulesByStateWithCreationDateOrder(@Param("state") ModuleState state,@Param("idTeam") UUID idTeam);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.moduleState = :state AND m.name like :name AND m.team.uuid = :idTeam ")
    List<Module> findAllModulesByStateAndNameWithoutOrder(@Param("state")ModuleState state, @Param("name")String name,@Param("idTeam") UUID idTeam);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.moduleState = :state AND m.name like :name AND m.team.uuid = :idTeam  " +
            "ORDER BY m.creationDate ASC")
    List<Module> findAllModulesByStateAndNameWithCreationDateOrder(@Param("state")ModuleState state, @Param("name")String name,@Param("idTeam") UUID idTeam);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.team.uuid = :idTeam  " +
            "ORDER BY m.creationDate ASC")
    List<Module> findAllModulesWithOrder(@Param("idTeam") UUID idTeam);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.team.uuid = :idTeam ")
    List<Module> findAllModulesWithoutOrder(@Param("idTeam") UUID idTeam);
}
