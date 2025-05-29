package com.equipo5.feelflowapp.dto.tablebadge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Schema(
        name = "TableBadgeDto",
        description = "Schema to hold table with badges awarded information"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TableBadgeAwardedDto {
    private UUID idUser;
    private String name;
    private String username;
    private CountBadgeAwardedDto manosAmigasBadge;
    private CountBadgeAwardedDto resolutorEstrellaBadge;
    private CountBadgeAwardedDto energiaPositivaBadge;
    private CountBadgeAwardedDto maestroDetalleBadge;

    public void incrementCountOfBadgeAwarded(String badgeName){
        switch (badgeName){
            case "MANOS_AMIGAS":
                this.getManosAmigasBadge().incrementCountAwarded();
                break;
            case "RESOLUTOR_ESTRELLA":
                this.getResolutorEstrellaBadge().incrementCountAwarded();
                break;
            case "ENERGIA_POSITIVA":
                this.getEnergiaPositivaBadge().incrementCountAwarded();
                break;
            case "MAESTRO_DETALLE":
                this.getMaestroDetalleBadge().incrementCountAwarded();
                break;
            default:
                break;
        }
    }

    public int getMaxCountOfBadgeAwarded(){
        int countMaxFriendHands = (this.getManosAmigasBadge() != null)? this.getManosAmigasBadge().getCountAwarded() : 0;
        int countResolutorEstrella = (this.getResolutorEstrellaBadge() != null)? this.getResolutorEstrellaBadge().getCountAwarded() : 0;
        int countEnergiaPositiva = (this.getEnergiaPositivaBadge() != null)? this.getEnergiaPositivaBadge().getCountAwarded() : 0;
        int countMaestroDetalle = (this.getMaestroDetalleBadge() != null)? this.getMaestroDetalleBadge().getCountAwarded() : 0;

        return countMaxFriendHands + countResolutorEstrella + countEnergiaPositiva + countMaestroDetalle;
    }

}
