package com.equipo5.feelflowapp.controller;


import com.equipo5.feelflowapp.constants.response.HttpResponses;
import com.equipo5.feelflowapp.dto.images.ImagesDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
import com.equipo5.feelflowapp.service.images.ImagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


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

    @Operation(
            summary = "Get logo image of the team",
            description = "REST API to get team image"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/team/current_team")
    @SecurityRequirement(name = "Bearer Authentication")
        public ImagesDto getImageOfTheCurrentTeam(){
        return imagesService.getImageOfTheCurrentTeam();
    }

    @Operation(
            summary = "Get logo image of the team",
            description = "REST API to get enterprise image"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/enterprise/current_enterprise")
    @SecurityRequirement(name = "Bearer Authentication")
    public ImagesDto getImageOfTheCurrentEnterprise(){
        return imagesService.getImageOfTheCurrentEnterprise();
    }

    @Operation(
            summary = "Load user image of the current user",
            description = "REST API to load or update an user image"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @PostMapping("/user/current_user")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDto> saveImageOfTheCurrentUser(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
         imagesService.saveImageOfTheUser(imageFile);

         return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
    }

    @Operation(
            summary = "Load user image of the current user by UUID",
            description = "REST API to load or update an user image by UUIDd"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @PostMapping("/user/{user_id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDto> saveImageOfTheCurrentUser(
            @PathVariable(name = "user_id") UUID userId,
            @RequestParam("imageFile") MultipartFile imageFile
    ) throws IOException {
        imagesService.saveImageOfTheUser(userId, imageFile);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
    }

    @Operation(
            summary = "Load logo image of the enterprise",
            description = "REST API to load or update enterprise image"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @PostMapping("/enterprise/current_enterprise")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDto> saveImageOfTheEnterprise(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        imagesService.saveImageOfTheEnterprise(imageFile);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
    }

    @Operation(
            summary = "Load logo image of the enterprise by UUID",
            description = "REST API to load or update enterprise image by UUID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @PostMapping("/enterprise/{enterprise_id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDto> saveImageOfTheEnterprise(
            @PathVariable(name = "enterprise_id") UUID enterpriseId,
            @RequestParam("imageFile") MultipartFile imageFile
    ) throws IOException {
        imagesService.saveImageOfTheEnterprise(enterpriseId, imageFile);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
    }

    @Operation(
            summary = "Load logo image of the team by UUID",
            description = "REST API to load or update team image by UUID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @PostMapping("/team/{team_id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDto> saveImageOfTheTeam(
            @PathVariable(name = "team_id") UUID team_id,
            @RequestParam("imageFile") MultipartFile imageFile
    ) throws IOException {
        imagesService.saveImageOfTheTeam(team_id, imageFile);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpResponses.STATUS_200,HttpResponses.MESSAGE_200));
    }

}
