package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModulesTypes;
import com.equipo5.feelflowapp.dto.module.ModuleDTO;
import com.equipo5.feelflowapp.service.module.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/module")
public class ModuleController {
    public  static final String MODULE_PATH = "/api/v1/module";

    public static final String PATH_ID = "/{idModule}";

    public static final String MODULE_NAME = "/{nameModule}";

    private final ModuleService moduleService;

    @PostMapping(value = MODULE_NAME)
    public ResponseEntity createModule(@PathVariable(value = "nameModule")ModulesTypes modulesTypes){

        ModuleDTO moduleDTO = moduleService.createModule(modulesTypes);

        return new ResponseEntity<>(moduleDTO, HttpStatus.CREATED);
    }


}
