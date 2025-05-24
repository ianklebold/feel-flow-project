package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.dto.badges.BadgesAvailableDto;
import com.equipo5.feelflowapp.dto.notifications.RecommendationDto;
import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.service.notification.recommendation.RecommendationService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Recommendation REST APIs",
        description = "REST APIs in Project to GET Recommendations"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = RecommendationController.MODULE_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class RecommendationController {
    public  static final String MODULE_PATH = "/api/v1/recommendations";

    private final RecommendationService recommendationService;

    @Operation(
            summary = "Get Recommendations REST API",
            description = "REST API to get recommendations"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping()
    public List<RecommendationDto> getRecommendations() {
        return recommendationService.getRecommendations();
    }

    @Operation(
            summary = "Post Recommendations REST API",
            description = "REST API to get recommendations"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping()
    public void postRecommendations() {
        recommendationService.sendRecommendation();
    }
}
