package com.equipo5.feelflowapp.dto.enterprise;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnterpriseInfoHomeDTO {
    private UUID uuid;
    private String name;
    //Aqui agregaremos las notificaciones o movimientos de los equipos
}
