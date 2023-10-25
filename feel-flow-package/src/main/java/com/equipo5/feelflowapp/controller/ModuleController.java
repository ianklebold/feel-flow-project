package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModulesTypes;
import com.equipo5.feelflowapp.dto.module.ModuleDTO;
import com.equipo5.feelflowapp.exception.module.TwelveStepsModuleException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamException;
import com.equipo5.feelflowapp.service.module.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/module")
public class ModuleController {
    public  static final String MODULE_PATH = "/api/v1/module";

    public static final String PATH_ID = "/{idModule}";

    public static final String MODULE_NAME = "/{nameModule}";

    public static final String MODULE_STATE = MODULE_NAME + "/{stateModule}";

    private final ModuleService moduleService;

    @PostMapping(value = MODULE_NAME)
    public ResponseEntity createModule(@PathVariable(value = "nameModule")ModulesTypes modulesTypes){

        try {
            ModuleDTO moduleDTO = moduleService.createModule(modulesTypes);
            return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);
        }catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (TwelveStepsModuleException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = MODULE_STATE)
    public List<ModuleDTO> getModules(@PathVariable(value = "nameModule")ModulesTypes modulesTypes,
                                      @PathVariable(value = "stateModule")ModuleState moduleState){

        moduleService.getModules(modulesTypes,moduleState);
        return null;
    }


}
