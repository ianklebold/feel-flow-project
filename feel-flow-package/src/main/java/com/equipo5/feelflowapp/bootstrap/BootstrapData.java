package com.equipo5.feelflowapp.bootstrap;

import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.users.Authority;
import com.equipo5.feelflowapp.repository.users.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Corriendo datos");

        loadAuthorityData();
    }

    private void loadAuthorityData(){
        if (authorityRepository.count() > 3){
            Authority teamLeaderAuthority = Authority.builder()
                    .teamRoles(TeamRoles.TEAM_LEADER)
                    .users(Collections.emptyList())
                    .build();

            Authority regularUserAuthority = Authority.builder()
                    .teamRoles(TeamRoles.USER_REGULAR)
                    .users(Collections.emptyList())
                    .build();

            Authority adminAuthority = Authority.builder()
                    .teamRoles(TeamRoles.ADMIN)
                    .users(Collections.emptyList())
                    .users(Collections.emptyList())
                    .build();

            authorityRepository.save(teamLeaderAuthority);
            authorityRepository.save(regularUserAuthority);
            authorityRepository.save(adminAuthority);
        }
    }


}
