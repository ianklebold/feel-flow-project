package com.equipo5.feelflowapp.service.summary;

import com.equipo5.feelflowapp.dto.summary.SummaryNikoNikoDto;

import java.util.List;
import java.util.UUID;

public interface SummaryNikoNikoService {
    List<SummaryNikoNikoDto> getSummary(UUID idTeam, Integer numberOfMonth);
}
