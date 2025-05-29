package com.equipo5.feelflowapp.service.tablebadge.kudos.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.modules.kudos.TableBadge;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.tablebadge.CountBadgeAwardedDto;
import com.equipo5.feelflowapp.dto.tablebadge.TableBadgeAwardedDto;
import com.equipo5.feelflowapp.mappers.tablebadge.BadgeAwardedMapper;
import com.equipo5.feelflowapp.repository.tablebadge.TableBadgeRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.module.kudos.KudosService;
import com.equipo5.feelflowapp.service.tablebadge.kudos.TableBadgeService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TableBadgeServiceImpl implements TableBadgeService {

    private final TableBadgeRepository tableBadgeRepository;

    protected final UserService userService;
    protected final UserRepository userRepository;
    protected final RegularUserRepository regularUserRepository;
    protected final TeamRepository teamRepository;
    protected final ModuleService moduleService;

    protected final BadgeAwardedMapper badgeAwardedMapper;


    @Override
    public void createTableBadge(KudosModule kudosModule) {
        kudosModule
                .getTeam().getRegularUsers()
                    .forEach(regularUser -> {
                        TableBadge tableBadge = new TableBadge();
                        tableBadge.setKudosModule( kudosModule );
                        tableBadge.setTableBadgeOwner( regularUser );
                        //tableBadgeRepository.save( tableBadge );

                        kudosModule.getTableBadge().add(tableBadge);
                    });

    }

    @Override
    public Optional<TableBadge> getTableBadgeByModuleAndIdUser(KudosModule kudosModule, UUID idUser) {

        return kudosModule.getTableBadge()
                .stream()
                .filter(tableBadge -> idUser.equals(tableBadge.getTableBadgeOwner().getUuid()) )
                .findFirst();

    }

    @Override
    public boolean isPossibleAssignBadge(TableBadge tableBadge, BadgeName badgeName) {

        if( BadgeName.ENERGIA_POSITIVA.equals(badgeName)  ){
            return tableBadge.getBadgePositiveEnergy() == null;
        }

        if( BadgeName.MAESTRO_DEL_DETALLE.equals(badgeName)  ){
            return tableBadge.getMasterOfDetail() == null;
        }

        if( BadgeName.RESOLUTOR_ESTRELLA.equals(badgeName)  ){
            return tableBadge.getBadgeResolutorStar() == null;
        }

        if( BadgeName.MANOS_AMIGAS.equals(badgeName)  ){
            var username = userService.getUsernameByCurrentUser();
            var regularUser = userRepository.findByUsername(username);
            if (regularUser.isPresent()) {
                var nameTeam = regularUserRepository.findTeamByUsername(username);
                Optional<Team> team = teamRepository.findById(UUID.fromString(nameTeam));

                if(team.isPresent()) {
                    return tableBadge.getBadgeFriendHands().size() < (team.get().getRegularUsers().size() - 1);
                }
            }
        }

        return false;
    }

    public boolean isAtLeastSentOneKudos(TableBadge tableBadge){

        return tableBadge.getBadgePositiveEnergy() != null
                || tableBadge.getMasterOfDetail() != null
                || tableBadge.getBadgeResolutorStar() != null
                || !tableBadge.getBadgeFriendHands().isEmpty();

    }

    @Override
    public void assignBadgeToTable(TableBadge tableBadge, Badge badge) {

        if( BadgeName.ENERGIA_POSITIVA.equals(badge.getBadgeName())  ){
            tableBadge.setBadgePositiveEnergy(badge);
        }

        if( BadgeName.MAESTRO_DEL_DETALLE.equals(badge.getBadgeName())  ){
            tableBadge.setMasterOfDetail(badge);
        }

        if( BadgeName.RESOLUTOR_ESTRELLA.equals(badge.getBadgeName())  ){
            tableBadge.setBadgeResolutorStar(badge);
        }

        if( BadgeName.MANOS_AMIGAS.equals(badge.getBadgeName())  ){
            tableBadge.getBadgeFriendHands().add(badge);
        }

        tableBadgeRepository.save( tableBadge );

    }

    @Override
    public void closeBadgeTable(TableBadge tableBadge) {

        var username = userService.getUsernameByCurrentUser();
        var regularUser = userRepository.findByUsername(username);
        if (regularUser.isPresent()) {
            var nameTeam = regularUserRepository.findTeamByUsername(username);
            Optional<Team> team = teamRepository.findById(UUID.fromString(nameTeam));
            if (team.isPresent()) {
                if ( tableBadge.getTableBadgeClosedDate() != null &&
                     tableBadge.getBadgeFriendHands().size() == team.get().getRegularUsers().size() &&
                     tableBadge.getBadgeResolutorStar() != null &&
                     tableBadge.getMasterOfDetail() != null &&
                     tableBadge.getBadgePositiveEnergy() != null
                ) {

                    tableBadge.setTableBadgeClosedDate(LocalDate.now() );

                }

            }
        }
    }

    @Override
    public List<TableBadgeAwardedDto> getTableBadgeDto(Team team) {
        List<KudosModule> moduleList =  this.moduleService.getModulesBy(ModuleNames.KUDOS.toString(), team);
        return getTableBadgeDto(moduleList);
    }

    @Override
    public List<TableBadgeAwardedDto> getTableBadgeDto(List<KudosModule> moduleList){
        List<TableBadgeAwardedDto> tableBadgeAwardedDtos = new ArrayList<>();
        if(!moduleList.isEmpty()) {
            //1. Obtener todos los usuarios del equipo perteneciente al modulo
            List<RegularUser> regularUsers = moduleList.get(0).getTeam().getRegularUsers();
            List<Badge> badges = new ArrayList<>();
            moduleList.stream()
                    .flatMap(module -> module.getTableBadge().stream() )
                    .forEach( tableBadges ->{
                                //2. Obtener todos los badges dados en un modulo.
                                if (tableBadges.getBadgePositiveEnergy() != null) badges.add(tableBadges.getBadgePositiveEnergy());
                                if (tableBadges.getMasterOfDetail() != null) badges.add(tableBadges.getMasterOfDetail());
                                if (tableBadges.getBadgeResolutorStar() != null) badges.add(tableBadges.getBadgeResolutorStar());
                                if (!tableBadges.getBadgeFriendHands().isEmpty()) badges.addAll(tableBadges.getBadgeFriendHands());
                            }
                    );

            if (!badges.isEmpty()) {
                tableBadgeAwardedDtos = regularUsers.stream()
                        .map(regularUser ->
                                TableBadgeAwardedDto.builder()
                                        .idUser(regularUser.getUuid())
                                        .username(regularUser.getUsername())
                                        .build()
                        )
                        .peek(tableBadgeAwardedDto -> {
                            List<Badge> badgesList = badges.stream()
                                    .filter( badge -> badge.getBadgeOwner().getUuid().equals(tableBadgeAwardedDto.getIdUser()) )
                                    .toList();

                            if (!badgesList.isEmpty()) {
                                badgesList.forEach(
                                        badge -> {createOrIncrement(tableBadgeAwardedDto, badge);}
                                );
                            }
                        })
                        .toList();
            }

        }
        return tableBadgeAwardedDtos;
    }

    @Override
    public int getNumberTotalOfBadgesBy(TableBadgeAwardedDto tableBadgeAwardedDto) {

        int total = 0;

        total = total + getNumberTotalOfBadgesBy(tableBadgeAwardedDto.getEnergiaPositivaBadge());
        total = total + getNumberTotalOfBadgesBy(tableBadgeAwardedDto.getMaestroDetalleBadge());
        total = total + getNumberTotalOfBadgesBy(tableBadgeAwardedDto.getManosAmigasBadge());
        total = total + getNumberTotalOfBadgesBy(tableBadgeAwardedDto.getResolutorEstrellaBadge());

        return total;
    }

    private int getNumberTotalOfBadgesBy(CountBadgeAwardedDto countBadgeAwardedDto){
        if(countBadgeAwardedDto == null){
            return 0;
        }else{
            return countBadgeAwardedDto.getCountAwarded();
        }
    }

    private void createOrIncrement(TableBadgeAwardedDto tableBadgeAwardedDto, Badge badge){
        switch (badge.getBadgeName().toString()){
            case "MANOS_AMIGAS":
                if (tableBadgeAwardedDto.getManosAmigasBadge() == null) {
                    tableBadgeAwardedDto.setManosAmigasBadge( badgeAwardedMapper.badgeToCountBadgeAwardedDto(badge) );
                }
                tableBadgeAwardedDto.getManosAmigasBadge().incrementCountAwarded();
                break;
            case "RESOLUTOR_ESTRELLA":
                if (tableBadgeAwardedDto.getResolutorEstrellaBadge() == null) {
                    tableBadgeAwardedDto.setResolutorEstrellaBadge( badgeAwardedMapper.badgeToCountBadgeAwardedDto(badge) );
                }
                tableBadgeAwardedDto.getResolutorEstrellaBadge().incrementCountAwarded();
                break;
            case "ENERGIA_POSITIVA":
                if (tableBadgeAwardedDto.getEnergiaPositivaBadge() == null) {
                    tableBadgeAwardedDto.setEnergiaPositivaBadge( badgeAwardedMapper.badgeToCountBadgeAwardedDto(badge) );
                }
                tableBadgeAwardedDto.getEnergiaPositivaBadge().incrementCountAwarded();
                break;
            case "MAESTRO_DEL_DETALLE":
                if (tableBadgeAwardedDto.getMaestroDetalleBadge() == null) {
                    tableBadgeAwardedDto.setMaestroDetalleBadge( badgeAwardedMapper.badgeToCountBadgeAwardedDto(badge) );
                }
                tableBadgeAwardedDto.getMaestroDetalleBadge().incrementCountAwarded();
                break;
            default:
                break;
        }
    }
}
