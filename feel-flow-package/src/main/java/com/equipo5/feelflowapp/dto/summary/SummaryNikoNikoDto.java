package com.equipo5.feelflowapp.dto.summary;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SummaryNikoNikoDto {
    private String name;
    private String surname;
    private Integer numberOfDay;
    private LocalDate closeDate;
    private String responseStartOfDay;
    private String responseEndOfDay;
}
