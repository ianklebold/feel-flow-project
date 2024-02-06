package com.equipo5.feelflowapp.repository.module;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module,Long> {

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.name like :name " +
            "ORDER BY m.creationDate ASC")
    List<Module> findAllModulesByNameWithCreationDateOrder(@Param("name")String name);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.name like :name ")
    List<Module> findAllModulesByNameWithoutOrder(@Param("name")String name);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.moduleState = :state")
    List<Module> findAllModulesByStateWithoutOrder(@Param("state")ModuleState state);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE  m.moduleState = :state " +
            "ORDER BY m.creationDate ASC")
    List<Module> findAllModulesByStateWithCreationDateOrder(@Param("state") ModuleState state);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.moduleState = :state AND m.name like :name")
    List<Module> findAllModulesByStateAndNameWithoutOrder(@Param("state")ModuleState state, @Param("name")String name);

    @Query(value ="SELECT m " +
            "FROM module as m " +
            "WHERE m.moduleState = :state AND m.name like :name " +
            "ORDER BY m.creationDate ASC")
    List<Module> findAllModulesByStateAndNameWithCreationDateOrder(@Param("state")ModuleState state, @Param("name")String name);
}
