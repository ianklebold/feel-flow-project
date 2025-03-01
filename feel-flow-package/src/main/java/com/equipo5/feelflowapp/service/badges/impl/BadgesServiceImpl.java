package com.equipo5.feelflowapp.service.badges.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.modules.kudos.TableBadge;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.badges.BadgeDto;
import com.equipo5.feelflowapp.dto.badges.BadgeTeamDto;
import com.equipo5.feelflowapp.dto.badges.BadgesAvailableDto;
import com.equipo5.feelflowapp.dto.badges.BadgesAwardedDto;
import com.equipo5.feelflowapp.exception.badrequest.badge.BadgeIsNotPossibleAssignException;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.mappers.badges.custom.BadgeAvailableMapper;
import com.equipo5.feelflowapp.mappers.badges.custom.BadgesDtoMapper;
import com.equipo5.feelflowapp.repository.badge.BadgeRepository;
import com.equipo5.feelflowapp.repository.module.KudosRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.badges.BadgesService;
import com.equipo5.feelflowapp.service.tablebadge.kudos.TableBadgeService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BadgesServiceImpl implements BadgesService {

    protected final UserService userService;

    protected final TableBadgeService tableBadgeService;

    protected final UserRepository userRepository;

    protected final RegularUserRepository regularUserRepository;

    protected final TeamRepository teamRepository;

    protected final KudosRepository kudosRepository;

    protected final BadgeRepository badgeRepository;

    protected final BadgeAvailableMapper badgeAvailableMapper;

    protected final BadgesDtoMapper badgesDtoMapper;

    private final List<BadgeName> BAGES_NAMES_LIST = List.of(
            BadgeName.MAESTRO_DEL_DETALLE,
            BadgeName.ENERGIA_POSITIVA,
            BadgeName.MANOS_AMIGAS,
            BadgeName.RESOLUTOR_ESTRELLA);

    @Override
    public void sendBadge(BadgesAwardedDto badgesAwardedDto) {
        var username = userService.getUsernameByCurrentUser();

        var regularUser = userRepository.findByUsername(username);
        if (regularUser.isPresent()) {
            var nameTeam = regularUserRepository.findTeamByUsername(username);
            Optional<Team> team = teamRepository.findById(UUID.fromString(nameTeam));

            if(team.isEmpty()){
                throw new NotFoundException("Equipo no encontrado");
            }

            Optional<KudosModule> kudosModule = this.kudosRepository.findByModuleStateAndTeam(ModuleState.ACTIVE, team.get());
            //Controlar que modulo este abierto
            if (kudosModule.isEmpty()) {
                throw new ModuleException("No existe modulo de kudos activo");
            }

            //Controlar que miembro a ser premiado exista en el mismo equipo.
            if( !memberExistInTeam( kudosModule.get().getTeam(), badgesAwardedDto.idMember() ) ){
                throw new NotFoundException("Usuario no encontrado en equipo");
            }

            //Controlar que usuario pueda premiar (limite de badges)
            Optional<TableBadge> tableBadge = this.tableBadgeService.getTableBadgeByModuleAndIdUser(kudosModule.get(), regularUser.get().getUuid());

            if (tableBadge.isEmpty()) {
                throw new NotFoundException("No existe tabla de premios para el usuario");
            }

            TableBadge tableBadgeEntity = tableBadge.get();
            boolean isPossibleAssignBadge = tableBadgeService.isPossibleAssignBadge(tableBadgeEntity, badgesAwardedDto.badgeName());
            if( isPossibleAssignBadge ){
                Badge badge = this.createBadge( badgesAwardedDto.badgeName(), badgesAwardedDto.idMember() );
                tableBadgeService.assignBadgeToTable(tableBadgeEntity, badge);
                tableBadgeService.closeBadgeTable(tableBadgeEntity);
            }else{
                throw new BadgeIsNotPossibleAssignException("No es posible asignar el badge a la tabla debido a que ya fue asignado");
            }
        }
    }

    @Override
    public List<BadgesAvailableDto> getBadgesAvailableToSend() {
        var username = userService.getUsernameByCurrentUser();

        var regularUser = userRepository.findByUsername(username);
        if (regularUser.isPresent()) {
            var nameTeam = regularUserRepository.findTeamByUsername(username);
            Optional<Team> team = teamRepository.findById(UUID.fromString(nameTeam));

            if (team.isEmpty()) {
                throw new NotFoundException("Equipo no encontrado");
            }

            Optional<KudosModule> kudosModule = this.kudosRepository.findByModuleStateAndTeam(ModuleState.ACTIVE, team.get());
            //Controlar que modulo este abierto
            if (kudosModule.isEmpty()) {
                throw new ModuleException("No existe modulo de kudos activo");
            }

            Optional<TableBadge> tableBadge = this.tableBadgeService.getTableBadgeByModuleAndIdUser(kudosModule.get(), regularUser.get().getUuid());
            if (tableBadge.isEmpty()) {
                throw new NotFoundException("No existe tabla de premios para el usuario");
            }

            TableBadge tableBadgeEntity = tableBadge.get();
            List<BadgesAvailableDto> badgesAvailableDtos = new ArrayList<>();

            this.badgeAvailableMapper.badgeToBadgeAvailableDto(tableBadgeEntity, badgesAvailableDtos);
            this.badgeAvailableMapper.badgeToBadgeAvailableDto(tableBadge.get(), team.get().getRegularUsers().size(), badgesAvailableDtos);
            return badgesAvailableDtos;
        }
        return List.of();
    }

    @Override
    public List<BadgeDto> getBadgesAwarded() {
        var username = userService.getUsernameByCurrentUser();

        var regularUser = userRepository.findByUsername(username);
        if (regularUser.isPresent()) {
            List<Badge> badges = this.badgeRepository.findAllByBadgeOwner( (RegularUser) regularUser.get() );
            return badges.stream()
                    .map(this.badgesDtoMapper::badgeToBadgeDto)
                    .toList();
        }
        return List.of();
    }

    @Override
    public List<BadgeTeamDto> getBadgeTeams() {
        var username = userService.getUsernameByCurrentUser();

        var regularUser = userRepository.findByUsername(username);
        if (regularUser.isPresent()) {
            var nameTeam = regularUserRepository.findTeamByUsername(username);
            Optional<Team> team = teamRepository.findById(UUID.fromString(nameTeam));

            if (team.isEmpty()) {
                throw new NotFoundException("Equipo no encontrado");
            }
            List<Badge> badges = new ArrayList<>();

            team.get().getRegularUsers()
                    .forEach(
                            member -> badges.addAll( this.badgeRepository.findAllByBadgeOwner( (RegularUser) member) )
                    );

            return BAGES_NAMES_LIST.stream()
                    .map(badgeName -> new BadgeTeamDto(badgeName, getNumberOfBadgesAwarded(badges, badgeName) ))
                    .toList();

        }
        return List.of();
    }

    private long getNumberOfBadgesAwarded(List<Badge> badges, BadgeName badgeName) {
        return badges.stream()
                .filter( badge -> badgeName.equals(badge.getBadgeName()) )
                .count();
    }

    private Badge createBadge(BadgeName badgeName, UUID idUser) {

        Optional<RegularUser> regularUser = this.regularUserRepository.findByUuid(idUser);

        Badge badge = new Badge();
        if (regularUser.isPresent()) {
            badge.setBadgeName( badgeName );
            badge.setAwardedDate(LocalDate.now());
            badge.setBadgeOwner(regularUser.get());
            return badgeRepository.save(badge);
        }
        return badge;
    }

    private boolean memberExistInTeam(Team team, UUID idUser) {
        return team.getRegularUsers()
                .stream()
                .anyMatch( regularUser -> idUser.equals(regularUser.getUuid())  );
    }


}
