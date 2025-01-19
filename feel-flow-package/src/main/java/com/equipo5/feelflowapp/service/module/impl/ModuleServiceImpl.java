package com.equipo5.feelflowapp.service.module.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.repository.module.ModuleRepository;
import com.equipo5.feelflowapp.repository.module.specification.ModuleSpecification;
import com.equipo5.feelflowapp.service.module.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private  final ModuleRepository moduleRepository;

    @Override
    public boolean isAnyModuleActive(final String name,final List<Module> modules) {
        return modules.stream()
                .filter(module -> module.getName().equals(name))
                .anyMatch(module -> module.getModuleState().toString().equals(ModuleState.ACTIVE.toString()));
    }

    @Override
    public List<SurveyModule> getSurveyModule(LocalDate creationDate, String name, Team team) {

        Specification<Module> spec = Specification.where(
                ModuleSpecification.withCreationDate(creationDate))
                .and(ModuleSpecification.withName(name))
                .and(ModuleSpecification.withTeam(team));


        return moduleRepository.findAll(spec)
                .stream()
                .map(survey -> (SurveyModule) survey )
                .toList();
    }
}
