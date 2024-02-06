package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.dto.modules.ModuleListDto;
import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.service.module.ModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Module  REST APIs",
        description = "REST APIs in Project to GET Modules"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = ModuleController.MODULE_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class ModuleController {
    public  static final String MODULE_PATH = "/api/v1/modules";

    private final ModuleService moduleService;

    @Operation(
            summary = "GET Modules REST API",
            description = "REST API to get modules"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping()
    public List<ModuleListDto> getModules(
            @RequestParam(required = false, name ="name") ModuleNames moduleName,
            @RequestParam(required = false, name ="state") ModuleState moduleState,
            @RequestParam(required = false, name = "creation_date_order",defaultValue = "true") String creationDateOrder
            ){

        return moduleService.getModules(moduleName,moduleState,creationDateOrder);
    }

}
