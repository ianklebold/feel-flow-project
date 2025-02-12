package com.equipo5.feelflowapp.service.module.kudos.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.dto.modules.CreationKudosModuleDto;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleAlreadyActiveException;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamException;
import com.equipo5.feelflowapp.repository.module.KudosRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.module.kudos.KudosService;
import com.equipo5.feelflowapp.service.tablebadge.kudos.TableBadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.KUDOS;
import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.NIKO_NIKO;

@Service
@RequiredArgsConstructor
public class KudosServiceImpl implements KudosService {

    private final KudosRepository kudosRepository;

    private final TeamRepository teamRepository;

    private final ModuleService moduleService;

    private final TableBadgeService tableBadgeService;


    @Override
    public void publishingModule(CreationKudosModuleDto creationKudosModule) {
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
            kudosModule.setDateAndTimeToPublish( creationKudosModule.dateAndTimeToPublish() );
            kudosModule.setDateAndTimeToClose( creationKudosModule.dateAndTimeToClose() );

            //Crear N Tablas de Badges.
            tableBadgeService.createTableBadge(kudosModule);

            kudosRepository.save(kudosModule);
        }else {
            throw new NotFoundTeamException("Equipo no encontrado");
        }

    }
}
