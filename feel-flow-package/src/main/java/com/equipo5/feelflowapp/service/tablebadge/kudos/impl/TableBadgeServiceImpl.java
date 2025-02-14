package com.equipo5.feelflowapp.service.tablebadge.kudos.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.modules.kudos.TableBadge;
import com.equipo5.feelflowapp.repository.tablebadge.TableBadgeRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.tablebadge.kudos.TableBadgeService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableBadgeServiceImpl implements TableBadgeService {

    private final TableBadgeRepository tableBadgeRepository;

    protected final UserService userService;
    protected final UserRepository userRepository;
    protected final RegularUserRepository regularUserRepository;
    protected final TeamRepository teamRepository;


    @Override
    public void createTableBadge(KudosModule kudosModule) {
        kudosModule
                .getTeam().getRegularUsers()
                    .forEach(regularUser -> {
                        TableBadge tableBadge = new TableBadge();
                        tableBadge.setKudosModule( kudosModule );
                        tableBadge.setTableBadgeOwner( regularUser );
                        tableBadgeRepository.save( tableBadge );

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
            return tableBadge.getBadgePositiveEnergy() == null;
        }

        if( BadgeName.RESOLUTOR_ESTRELLA.equals(badgeName)  ){
            return tableBadge.getBadgePositiveEnergy() == null;
        }

        if( BadgeName.MANOS_AMIGAS.equals(badgeName)  ){
            var username = userService.getUsernameByCurrentUser();
            var regularUser = userRepository.findByUsername(username);
            if (regularUser.isPresent()) {
                var nameTeam = regularUserRepository.findTeamByUsername(username);
                Optional<Team> team = teamRepository.findById(UUID.fromString(nameTeam));

                if(team.isPresent()) {
                    return tableBadge.getBadgeFriendHands().size() < team.get().getRegularUsers().size();
                }
            }
        }

        return false;
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


}
