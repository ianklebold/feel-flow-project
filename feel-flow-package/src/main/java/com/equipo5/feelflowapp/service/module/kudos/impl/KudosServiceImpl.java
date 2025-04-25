package com.equipo5.feelflowapp.service.module.kudos.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.dto.modules.CreationKudosModuleDto;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleAlreadyActiveException;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamException;
import com.equipo5.feelflowapp.repository.module.KudosRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.module.kudos.KudosService;
import com.equipo5.feelflowapp.service.tablebadge.kudos.TableBadgeService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

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

            if( creationKudosModule.dateAndTimeToClose().isBefore( creationKudosModule.dateAndTimeToPublish() )
                    || creationKudosModule.dateAndTimeToClose().isEqual( creationKudosModule.dateAndTimeToPublish() )
            ){
                throw new ModuleException("La fecha de cierre es igual o antes que la fecha de creacion");
            }

            //Creacion de modulo Kudos
            KudosModule kudosModule = new KudosModule();
            kudosModule.setCreationDate( LocalDate.now() );
            kudosModule.setModuleState( ModuleState.ACTIVE );
            kudosModule.setName( KUDOS.toString() );
            kudosModule.setTeam( team.get() );
            kudosModule.setDateAndTimeToPublish(Timestamp.valueOf(creationKudosModule.dateAndTimeToPublish()));
            kudosModule.setDateAndTimeToClose(Timestamp.valueOf(creationKudosModule.dateAndTimeToClose()));

            //Crear N Tablas de Badges.
            tableBadgeService.createTableBadge(kudosModule);

            return kudosRepository.save(kudosModule);
        }else {
            throw new NotFoundTeamException("Equipo no encontrado");
        }
    }

    @Override
    public void closeModule() {

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
                kudosRepository.save(kudosModule.get());
            }

        }

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
}
