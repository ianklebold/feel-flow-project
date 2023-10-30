package com.equipo5.feelflowapp.bootstrap;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.domain.users.Authority;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.repository.enterprise.EnterpriseRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.AuthorityRepository;
import com.equipo5.feelflowapp.repository.users.admin.AdminRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.repository.users.teamleader.TeamLeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements CommandLineRunner {
    private final TeamRepository teamRepository;

    private final AuthorityRepository authorityRepository;

    private final AdminRepository adminRepository;

    private final TeamLeaderRepository teamLeaderRepository;

    private final RegularUserRepository regularUserRepository;

    private final PasswordEncoder passwordEncoder;

    private static final  String PASSWORD_TEMPLATE = "RiverPlatecapo@123";
    @Override
    public void run(String... args) throws Exception {
        log.info("Corriendo datos");

        loadAuthorityData();
        loadAdminAndEnterprises();
        loadTeamsAndTeamLeader();
        loadMembers();
    }

    private void loadMembers(){
        if(regularUserRepository.count() < 3){

            String uuidTeam = teamLeaderRepository.findTeamByUsername("tlteam1@gmail.com");
            Team team1 = teamRepository.findById(UUID.fromString(uuidTeam)).get();
            RegularUser regularUser = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member1")
                    .surname("member1")
                    .username("member1@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team1)
                    .build();

            RegularUser regularUser2 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member2")
                    .surname("member2")
                    .username("member2@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team1)
                    .build();

            RegularUser regularUser3 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member3")
                    .surname("member3")
                    .username("member3@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team1)
                    .build();

            regularUserRepository.saveAll(List.of(regularUser,regularUser2,regularUser3));

            String uuidTeam2 = teamLeaderRepository.findTeamByUsername("tlteam2@gmail.com");
            Team team2 = teamRepository.findById(UUID.fromString(uuidTeam2)).get();

            RegularUser regularUser4 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member4")
                    .surname("member4")
                    .username("member4@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team2)
                    .build();

            RegularUser regularUser5 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member5")
                    .surname("member5")
                    .username("member5@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team2)
                    .build();

            RegularUser regularUser6 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member6")
                    .surname("member6")
                    .username("member6@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team2)
                    .build();

            regularUserRepository.saveAll(List.of(regularUser4,regularUser5,regularUser6));

            String uuidTeam3 = teamLeaderRepository.findTeamByUsername("tlteam3@gmail.com");
            Team team3 = teamRepository.findById(UUID.fromString(uuidTeam3)).get();

            RegularUser regularUser7 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member7")
                    .surname("member7")
                    .username("member7@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team3)
                    .build();

            RegularUser regularUser8 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member8")
                    .surname("member8")
                    .username("member8@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team3)
                    .build();

            RegularUser regularUser9 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member9")
                    .surname("member9")
                    .username("member9@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team3)
                    .build();

            regularUserRepository.saveAll(List.of(regularUser7,regularUser8,regularUser9));
        }
    }

    private void loadTeamsAndTeamLeader(){
        if (teamLeaderRepository.count() < 3){
            TeamLeader teamLeader1 = TeamLeader.builder()
                    .uuid(UUID.randomUUID())
                    .name("tlteam1")
                    .surname("tlteam1")
                    .username("tlteam1@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.TEAM_LEADER).get()))
                    .build();

            Team team1 = Team.builder()
                    .uuid(UUID.randomUUID())
                    .name("team1")
                    .teamLeader(teamLeader1)
                    .descriptionProject("descripcion1")
                    .regularUsers(Collections.emptyList())
                    .enterPrise(adminRepository.findByUsername("admin1@gmail.com").get().getEnterPrise())
                    .build();

            teamRepository.save(team1);

            TeamLeader teamLeader2 = TeamLeader.builder()
                    .uuid(UUID.randomUUID())
                    .name("tlteam2")
                    .surname("tlteam2")
                    .username("tlteam2@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.TEAM_LEADER).get()))
                    .build();

            Team team2 = Team.builder()
                    .uuid(UUID.randomUUID())
                    .name("team2")
                    .teamLeader(teamLeader2)
                    .descriptionProject("descripcion2")
                    .regularUsers(Collections.emptyList())
                    .enterPrise(adminRepository.findByUsername("admin2@gmail.com").get().getEnterPrise())
                    .build();

            teamRepository.save(team2);

            TeamLeader teamLeader3 = TeamLeader.builder()
                    .uuid(UUID.randomUUID())
                    .name("tlteam3")
                    .surname("tlteam3")
                    .username("tlteam3@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.TEAM_LEADER).get()))
                    .build();

            Team team3 = Team.builder()
                    .uuid(UUID.randomUUID())
                    .name("team3")
                    .teamLeader(teamLeader3)
                    .descriptionProject("descripcion3")
                    .regularUsers(Collections.emptyList())
                    .enterPrise(adminRepository.findByUsername("admin3@gmail.com").get().getEnterPrise())
                    .build();

            teamRepository.save(team3);
        }
    }

    private void loadAdminAndEnterprises(){
        if(adminRepository.count() < 3){
            Admin admin1 = Admin.builder()
                    .uuid(UUID.randomUUID())
                    .name("admin1")
                    .surname("admin1")
                    .username("admin1@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.ADMIN).get()))
                    .build();

            EnterPrise enterPrise1 = EnterPrise.builder()
                    .uuid(UUID.randomUUID())
                    .name("EmpresaAdmin1")
                    .team(Collections.emptyList())
                    .admin(admin1)
                    .build();

            admin1.setEnterPrise(enterPrise1);
            adminRepository.save(admin1);

            Admin admin2 = Admin.builder()
                    .uuid(UUID.randomUUID())
                    .name("admin2")
                    .surname("admin2")
                    .username("admin2@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.ADMIN).get()))
                    .build();

            EnterPrise enterPrise2 = EnterPrise.builder()
                    .uuid(UUID.randomUUID())
                    .name("EmpresaAdmin2")
                    .team(Collections.emptyList())
                    .admin(admin2)
                    .build();

            admin2.setEnterPrise(enterPrise2);
            adminRepository.save(admin2);

            Admin admin3 = Admin.builder()
                    .uuid(UUID.randomUUID())
                    .name("admin3")
                    .surname("admin3")
                    .username("admin3@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.ADMIN).get()))
                    .build();

            EnterPrise enterPrise3 = EnterPrise.builder()
                    .uuid(UUID.randomUUID())
                    .name("EmpresaAdmin3")
                    .team(Collections.emptyList())
                    .admin(admin3)
                    .build();

            admin3.setEnterPrise(enterPrise3);
            adminRepository.save(admin3);

        }
    }

    private void loadAuthorityData(){
        if (authorityRepository.count() < 3){
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
