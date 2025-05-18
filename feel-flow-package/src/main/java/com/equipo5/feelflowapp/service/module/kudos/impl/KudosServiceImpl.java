package com.equipo5.feelflowapp.service.module.kudos.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.modules.kudos.TableBadge;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.dashboard.kudos.KudosSummaryData;
import com.equipo5.feelflowapp.dto.modules.CreationKudosModuleDto;
import com.equipo5.feelflowapp.dto.tablebadge.TableBadgeAwardedDto;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleAlreadyActiveException;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamException;
import com.equipo5.feelflowapp.repository.module.KudosRepository;
import com.equipo5.feelflowapp.repository.module.ModuleRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.dashboard.DashboardService;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.module.kudos.KudosService;
import com.equipo5.feelflowapp.service.tablebadge.kudos.TableBadgeService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.KUDOS;

@Service
@RequiredArgsConstructor
public class KudosServiceImpl implements KudosService {

    private final KudosRepository kudosRepository;

    private final TeamRepository teamRepository;

    private final ModuleService moduleService;

    private final TableBadgeService tableBadgeService;

    protected final UserService userService;

    protected final UserRepository userRepository;

    protected final RegularUserRepository regularUserRepository;

    protected final ModuleRepository moduleRepository;

    protected final DashboardService dashboardService;

    @Override
    public KudosModule publishingModule(CreationKudosModuleDto creationKudosModule) {
        Optional<Team> team = teamRepository.findById(creationKudosModule.idTeam());

        if( team.isPresent() ) {
            var currentTeam = team.get();
            boolean existModuleActive =  moduleService.isAnyModuleActive(KUDOS.toString(),currentTeam.getModules());

            if (existModuleActive){
                //Error retornar excepcion
                throw new ModuleAlreadyActiveException("Actualmente se tiene un modulo de Kudos activo");
            }

            if( creationKudosModule.dateAndTimeToClose().toLocalDateTime().isBefore( creationKudosModule.dateAndTimeToPublish().toLocalDateTime() )
                    || creationKudosModule.dateAndTimeToClose().toLocalDateTime().isEqual( creationKudosModule.dateAndTimeToPublish().toLocalDateTime() )
            ){
                throw new ModuleException("La fecha de cierre es igual o antes que la fecha de creacion");
            }

            //Creacion de modulo Kudos
            KudosModule kudosModule = new KudosModule();
            kudosModule.setCreationDate( LocalDate.now() );
            kudosModule.setModuleState( ModuleState.ACTIVE );
            kudosModule.setName( KUDOS.toString() );
            kudosModule.setTeam( team.get() );
            kudosModule.setDateAndTimeToPublish(creationKudosModule.dateAndTimeToPublish());
            kudosModule.setDateAndTimeToClose(creationKudosModule.dateAndTimeToClose());

            //Crear N Tablas de Badges.
            tableBadgeService.createTableBadge(kudosModule);

            return kudosRepository.save(kudosModule);
        }else {
            throw new NotFoundTeamException("Equipo no encontrado");
        }
    }

    @Override
    public KudosModule closeModule() {

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

            boolean isReadyToClose = kudosModule.get().getTableBadge()
                    .stream()
                    .allMatch( tableBadge -> tableBadge.getTableBadgeClosedDate() != null );

            if (isReadyToClose){
                kudosModule.get().setModuleClosedDate(LocalDate.now());
                kudosModule.get().setModuleState(ModuleState.FINISHED);
                KudosModule kudosModuleSaved = kudosRepository.save(kudosModule.get());
                return kudosModuleSaved;
            }

        }
        return null;
    }

    @Override
    public boolean isModuleKudosAvailable() {
        var username = userService.getUsernameByCurrentUser();
        var regularUser = userRepository.findByUsername(username);
        if (regularUser.isPresent()) {
            var nameTeam = regularUserRepository.findTeamByUsername(username);
            Optional<Team> team = teamRepository.findById(UUID.fromString(nameTeam));

            if (team.isEmpty()) {
                throw new NotFoundException("Equipo no encontrado");
            }

            Optional<KudosModule> kudosModule = this.kudosRepository.findByModuleStateAndTeam(ModuleState.ACTIVE, team.get());
            return kudosModule.isPresent();
        }
        return false;
    }

    @Override
    public List<RegularUser> usersAwardedByModule(KudosModule kudosModule) {
        return kudosModule.getTableBadge().stream()
                .flatMap(
                        tableBadge ->
                                Stream.of(tableBadge.getMasterOfDetail(),
                                        tableBadge.getBadgePositiveEnergy(),
                                        tableBadge.getBadgeResolutorStar(),
                                        tableBadge.getBadgeFriendHands()
                                        )
                )
                .filter(Objects::nonNull)
                .map(b -> (Badge) b)
                .map(Badge::getBadgeOwner)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                RegularUser::getUuid,
                                u -> u,
                                (u1, u2) -> u1
                        ),
                        m -> List.copyOf(m.values())
                ));
    }

    @Override
    public double percentOfModuleCompleted(Team team) {

        AtomicInteger completed = new AtomicInteger();
        completed.set(0);


            int cantOfMembers = team.getRegularUsers().size();
            List<Module> modules =  this.moduleRepository.findModulesByNameAndTeamOrderByIdDescCreationDateDesc(KUDOS.toString(), team);

            if (!modules.isEmpty()){
                KudosModule kudosModule = (KudosModule) modules.get(0);

                kudosModule.getTableBadge()
                        .forEach(
                                kudosTable -> {
                                    if ( tableBadgeService.isAtLeastSentOneKudos(kudosTable) ) {
                                        completed.incrementAndGet();
                                    }
                                }
                        );
            }

            return (double) completed.get() / cantOfMembers;
    }

    @Override
    public int countOfKudosSent(Team team) {

        List<Module> modules =  this.moduleRepository.findModulesByNameAndTeamOrderByIdDescCreationDateDesc(KUDOS.toString(), team);
        KudosModule kudosModule = (KudosModule) modules.get(0);

        List<TableBadgeAwardedDto> kudosSummaryData =  tableBadgeService.getTableBadgeDto(List.of(kudosModule));

        return getCountOfKudosSent( kudosSummaryData );

    }

    private int getCountOfKudosSent(List<TableBadgeAwardedDto> kudosSummaryData){
        return kudosSummaryData.stream()
                .map( kudos -> kudos.getMaestroDetalleBadge().getCountAwarded() +
                        kudos.getEnergiaPositivaBadge().getCountAwarded() +
                        kudos.getManosAmigasBadge().getCountAwarded() +
                        kudos.getResolutorEstrellaBadge().getCountAwarded()
                ).reduce(0, Integer::sum);
    }

    @Override
    public double happinessByKudosModule(Team team) {
        int countOfMembers = team.getRegularUsers().size();
        List<Module> modules =  this.moduleRepository.findModulesByNameAndTeamOrderByIdDescCreationDateDesc(KUDOS.toString(), team);
        KudosModule kudosModule = (KudosModule) modules.get(0);
        List<TableBadgeAwardedDto> kudosSummaryData =  tableBadgeService.getTableBadgeDto(List.of(kudosModule));


        long countOfKudosSent = getCountOfKudosSent(kudosSummaryData);
        long countMaxOfKudosForSend = (long) (3 + (team.getRegularUsers().size() - 1)) * team.getRegularUsers().size();
        long countOfMembersWhoSentFriendHands = kudosSummaryData.stream()
                .filter( kudosSummary -> kudosSummary.getManosAmigasBadge() != null && kudosSummary.getManosAmigasBadge().getCountAwarded() > 0 )
                .count();

        return ( (double) countOfMembersWhoSentFriendHands / countOfMembers ) * 0.7 / ( (double) countOfKudosSent / countMaxOfKudosForSend) * 0.3;
    }

    @Override
    public String getEmotionalStateByKudosHappiness(double happiness) {

        if (happiness >= 0.5){
            return "Positivo";
        }

        return "Negativo";

    }

}
