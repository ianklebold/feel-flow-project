package com.equipo5.feelflowapp.service.summary.kudos.impl;

import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.modules.kudos.TableBadge;
import com.equipo5.feelflowapp.dto.badges.BadgeTeamDto;
import com.equipo5.feelflowapp.repository.module.ModuleRepository;
import com.equipo5.feelflowapp.service.summary.kudos.SummaryKudosService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
public class SummaryKudosServiceImpl implements SummaryKudosService {
    private final ModuleRepository moduleRepository;


    @Override
    public List<BadgeTeamDto> getSummary(Long idModule, UUID idRegularUser) {
        List<BadgeTeamDto>  badgeTeamDtos = new ArrayList<>();
        Map<BadgeName, Long> countOfBadges = new HashMap<>();

        if(idModule != null ){
            Optional<Module> module = moduleRepository.findById(idModule);
            if(module.isEmpty()){
                return List.of();
            }
            KudosModule kudosModule = (KudosModule) module.get();
            List<TableBadge> tableBadges;
            if(idRegularUser != null){
                tableBadges = kudosModule.getTableBadge()
                        .stream()
                        .filter(tableBadge -> {
                            if (tableBadge.getMasterOfDetail() != null && tableBadge.getMasterOfDetail().getBadgeOwner().getUuid().equals(idRegularUser)) {
                                return true;
                            }
                            if (tableBadge.getBadgePositiveEnergy() != null && tableBadge.getBadgePositiveEnergy().getBadgeOwner().getUuid().equals(idRegularUser)) {
                                return true;
                            }
                            if (tableBadge.getBadgeResolutorStar() != null && tableBadge.getBadgeResolutorStar().getBadgeOwner().getUuid().equals(idRegularUser)) {
                                return true;
                            }
                            if (!tableBadge.getBadgeFriendHands().isEmpty()  && tableBadge.getBadgeFriendHands().stream().anyMatch(badge -> badge.getBadgeOwner().getUuid().equals(idRegularUser))) {
                                return true;
                            }
                            return false;
                        }).toList();
                countOfBadges.put(BadgeName.MAESTRO_DEL_DETALLE, getNumberOfBadges(BadgeName.MAESTRO_DEL_DETALLE,tableBadges,idRegularUser));
                countOfBadges.put(BadgeName.ENERGIA_POSITIVA, getNumberOfBadges(BadgeName.ENERGIA_POSITIVA,tableBadges,idRegularUser));
                countOfBadges.put(BadgeName.RESOLUTOR_ESTRELLA, getNumberOfBadges(BadgeName.RESOLUTOR_ESTRELLA,tableBadges,idRegularUser));
                countOfBadges.put(BadgeName.MANOS_AMIGAS, getNumberOfBadges(BadgeName.MANOS_AMIGAS,tableBadges,idRegularUser));
            }else{
                tableBadges = new ArrayList<>(kudosModule.getTableBadge());
                countOfBadges.put(BadgeName.MAESTRO_DEL_DETALLE, getNumberOfBadges(BadgeName.MAESTRO_DEL_DETALLE,tableBadges));
                countOfBadges.put(BadgeName.ENERGIA_POSITIVA, getNumberOfBadges(BadgeName.ENERGIA_POSITIVA,tableBadges));
                countOfBadges.put(BadgeName.RESOLUTOR_ESTRELLA, getNumberOfBadges(BadgeName.RESOLUTOR_ESTRELLA,tableBadges));
                countOfBadges.put(BadgeName.MANOS_AMIGAS, getNumberOfBadges(BadgeName.MANOS_AMIGAS,tableBadges));

            }

            badgeTeamDtos.add(new BadgeTeamDto(BadgeName.MAESTRO_DEL_DETALLE, countOfBadges.get(BadgeName.MAESTRO_DEL_DETALLE)));
            badgeTeamDtos.add(new BadgeTeamDto(BadgeName.ENERGIA_POSITIVA, countOfBadges.get(BadgeName.ENERGIA_POSITIVA)));
            badgeTeamDtos.add(new BadgeTeamDto(BadgeName.RESOLUTOR_ESTRELLA, countOfBadges.get(BadgeName.RESOLUTOR_ESTRELLA)));
            badgeTeamDtos.add(new BadgeTeamDto(BadgeName.MANOS_AMIGAS, countOfBadges.get(BadgeName.MANOS_AMIGAS)));
            return badgeTeamDtos;
        }

        return List.of();
    }

    private long getNumberOfBadges(BadgeName badgeName, List<TableBadge> tableBadges){

        if (BadgeName.MAESTRO_DEL_DETALLE.equals(badgeName)) {
            return tableBadges.stream()
                    .filter( badge -> badge.getMasterOfDetail() != null)
                    .count();
        }

        if (BadgeName.RESOLUTOR_ESTRELLA.equals(badgeName)) {
            return tableBadges.stream()
                    .filter( badge -> badge.getBadgeResolutorStar() != null)
                    .count();
        }

        if (BadgeName.ENERGIA_POSITIVA.equals(badgeName)) {
            return tableBadges.stream()
                    .filter( badge -> badge.getBadgePositiveEnergy() != null)
                    .count();
        }

        if (BadgeName.MANOS_AMIGAS.equals(badgeName)) {
            AtomicLong result = new AtomicLong();
            tableBadges.stream()
                    .filter( badge -> !badge.getBadgeFriendHands().isEmpty())
                    .map(badge -> badge.getBadgeFriendHands().size())
                    .forEach(result::addAndGet);
            return result.get();
        }

        return 0L;
    }

    private long getNumberOfBadges(BadgeName badgeName, List<TableBadge> tableBadges, UUID idRegularUser){

        if (BadgeName.MAESTRO_DEL_DETALLE.equals(badgeName)) {
            return tableBadges.stream()
                    .filter( badge -> badge.getMasterOfDetail() != null && badge.getMasterOfDetail().getBadgeOwner().getUuid().equals(idRegularUser))
                    .count();
        }

        if (BadgeName.RESOLUTOR_ESTRELLA.equals(badgeName)) {
            return tableBadges.stream()
                    .filter( badge -> badge.getBadgeResolutorStar() != null && badge.getBadgeResolutorStar().getBadgeOwner().getUuid().equals(idRegularUser))
                    .count();
        }

        if (BadgeName.ENERGIA_POSITIVA.equals(badgeName)) {
            return tableBadges.stream()
                    .filter( badge -> badge.getBadgePositiveEnergy() != null && badge.getBadgePositiveEnergy().getBadgeOwner().getUuid().equals(idRegularUser))
                    .count();
        }

        if (BadgeName.MANOS_AMIGAS.equals(badgeName)) {
            return tableBadges.stream()
                    .filter( badge -> !badge.getBadgeFriendHands().isEmpty())
                    .flatMap(badge -> badge.getBadgeFriendHands().stream())
                    .filter( badge -> badge.getBadgeOwner().getUuid().equals(idRegularUser))
                    .count();
        }

        return 0L;
    }

}
