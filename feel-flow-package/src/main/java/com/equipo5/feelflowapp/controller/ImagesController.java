package com.equipo5.feelflowapp.controller;


import com.equipo5.feelflowapp.dto.images.ImagesDto;
import com.equipo5.feelflowapp.service.images.ImagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(
        name = "Images REST APIs",
        description = "REST APIs in Project to GET Images"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = ImagesController.MODULE_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class ImagesController {
    public  static final String MODULE_PATH = "/api/v1/images";

    private final ImagesService imagesService;

    @Operation(
            summary = "Get user image of the current user",
            description = "REST API to get user image"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/user/current_user")
    @SecurityRequirement(name = "Bearer Authentication")
    public ImagesDto getImageOfTheCurrentUser(){
        return imagesService.getImageOfTheCurrentUser();
    }

}
