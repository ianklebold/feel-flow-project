package com.equipo5.feelflowapp.bootstrap;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.images.MediaImage;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
    private static final  String PASSWORD_TEMPLATE_SECOND = "Proyectofinalfeelflow@123";
    @Override
    public void run(String... args) throws Exception {
        log.info("Corriendo datos");
        loadAuthorityData();
        loadAdminAndEnterprises();
        loadTeamsAndTeamLeader();
        loadMembers();
    }


    private String convertToBase64(InputStream inputStream) throws IOException {
        return Base64.getEncoder().encodeToString(inputStream.readAllBytes());
    }

    private void loadMembers() throws IOException {
        if(regularUserRepository.count() < 16){


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
                    .mediaImage(
                            MediaImage.builder()
                                    .name("member1_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64(new ClassPathResource("images/profile/regularuser/regular_user_1.jpeg").getInputStream())
                                    )
                                    .build()
                    )
                    .build();

            RegularUser regularUser2 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member2")
                    .surname("member2")
                    .username("member2@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team1)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("member2_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64(new ClassPathResource(("images/profile/regularuser/regular_user_2.jpeg")).getInputStream())
                                    )
                                    .build()
                    )
                    .build();

            RegularUser regularUser3 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member3")
                    .surname("member3")
                    .username("member3@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team1)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("member3_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64(new ClassPathResource(("images/profile/regularuser/regular_user_3.jpeg")).getInputStream())
                                    )
                                    .build()
                    )
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
                    .mediaImage(
                            MediaImage.builder()
                                    .name("member4_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64(new ClassPathResource(("images/profile/regularuser/regular_user_4.jpeg")).getInputStream())
                                    )
                                    .build()
                    )
                    .build();

            RegularUser regularUser5 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member5")
                    .surname("member5")
                    .username("member5@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team2)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("member5_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/regularuser/regular_user_5.jpeg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            RegularUser regularUser6 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member6")
                    .surname("member6")
                    .username("member6@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team2)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("member6_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/regularuser/regular_user_6.jpeg")).getInputStream() )
                                    )
                                    .build()
                    )
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
                    .mediaImage(
                            MediaImage.builder()
                                    .name("member7_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                           convertToBase64( new ClassPathResource(("images/profile/regularuser/regular_user_7.jpeg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            RegularUser regularUser8 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member8")
                    .surname("member8")
                    .username("member8@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team3)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("member8_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/regularuser/regular_user_8.jpeg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            RegularUser regularUser9 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member9")
                    .surname("member9")
                    .username("member9@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team3)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("member9_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/regularuser/regular_user_9.jpeg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            regularUserRepository.saveAll(List.of(regularUser7,regularUser8,regularUser9));

            String uuidTeam4 = teamLeaderRepository.findTeamByUsername("matiass@gmail.com");
            Team team4 = teamRepository.findById(UUID.fromString(uuidTeam4)).get();

            RegularUser regularUser10 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("Facundo")
                    .surname("Bordes")
                    .username("facub@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE_SECOND))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team4)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("facu_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                           convertToBase64( new ClassPathResource(("images/profile/regularuser/facub.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            RegularUser regularUser11 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("Melina")
                    .surname("Teruel")
                    .username("melinat@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE_SECOND))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team4)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("meli_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/regularuser/melit.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            RegularUser regularUser12 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("member12")
                    .surname("member12")
                    .username("member12@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE_SECOND))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team4)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("member12_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/regularuser/regular_user_9.jpeg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            regularUserRepository.saveAll(List.of(regularUser10,regularUser11,regularUser12));

            String uuidTeam5 = teamLeaderRepository.findTeamByUsername("cesartl@gmail.com");
            Team team5 = teamRepository.findById(UUID.fromString(uuidTeam5)).get();

            RegularUser regularUser13 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("Graciela")
                    .surname("Mendez")
                    .username("graciela@gmail.com")
                    .country("Argentina")
                    .phoneNumber("36241234567")
                    .description("Nueva en el equipo feel flow")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE_SECOND))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team5)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("graciela_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/regularuser/gracielam.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            RegularUser regularUser14 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("Ian")
                    .surname("Fernandez")
                    .country("USA")
                    .phoneNumber("36241234567")
                    .description("Soy un hombre vencido, pero no me doy por vencido")
                    .username("ianf@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE_SECOND))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team5)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("ian_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/regularuser/ianf.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            RegularUser regularUser15 = RegularUser.builder()
                    .uuid(UUID.randomUUID())
                    .name("Jorge")
                    .surname("Menegaz")
                    .username("jorgeh@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE_SECOND))
                    .country("Argentina")
                    .phoneNumber("36241234567")
                    .description("Soy una persona curiosa.")
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.USER_REGULAR).get()))
                    .team(team5)
                    .mediaImage(
                            MediaImage.builder()
                                    .name("jorge_image")
                                    .fileType("image/jpeg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/regularuser/jorgeh.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            regularUserRepository.saveAll(List.of(regularUser13,regularUser14,regularUser15));

        }
    }

    private void loadTeamsAndTeamLeader() throws IOException {
        if (teamLeaderRepository.count() < 5){
            TeamLeader teamLeader1 = TeamLeader.builder()
                    .uuid(UUID.randomUUID())
                    .name("tlteam1")
                    .surname("tlteam1")
                    .username("tlteam1@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.TEAM_LEADER).get()))
                    .mediaImage(
                            MediaImage.builder()
                                    .name("teamleader1_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/teamleader/team_leader1.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            Team team1 = Team.builder()
                    .uuid(UUID.randomUUID())
                    .name("team1")
                    .teamLeader(teamLeader1)
                    .descriptionProject("descripcion1")
                    .regularUsers(Collections.emptyList())
                    .enterPrise(adminRepository.findByUsername("admin1@gmail.com").get().getEnterPrise())
                    .logo(
                            MediaImage.builder()
                                    .name("team1_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/team/team1.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            teamRepository.save(team1);

            TeamLeader teamLeader2 = TeamLeader.builder()
                    .uuid(UUID.randomUUID())
                    .name("tlteam2")
                    .surname("tlteam2")
                    .username("tlteam2@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.TEAM_LEADER).get()))
                    .mediaImage(
                            MediaImage.builder()
                                    .name("teamleader2_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/teamleader/team_leader2.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            Team team2 = Team.builder()
                    .uuid(UUID.randomUUID())
                    .name("team2")
                    .teamLeader(teamLeader2)
                    .descriptionProject("descripcion2")
                    .regularUsers(Collections.emptyList())
                    .enterPrise(adminRepository.findByUsername("admin2@gmail.com").get().getEnterPrise())
                    .logo(
                            MediaImage.builder()
                                    .name("team2_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/team/team2.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            teamRepository.save(team2);

            TeamLeader teamLeader3 = TeamLeader.builder()
                    .uuid(UUID.randomUUID())
                    .name("tlteam3")
                    .surname("tlteam3")
                    .username("tlteam3@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.TEAM_LEADER).get()))
                    .mediaImage(
                            MediaImage.builder()
                                    .name("teamleader3_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/teamleader/team_leader3.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            Team team3 = Team.builder()
                    .uuid(UUID.randomUUID())
                    .name("team3")
                    .teamLeader(teamLeader3)
                    .descriptionProject("descripcion3")
                    .regularUsers(Collections.emptyList())
                    .enterPrise(adminRepository.findByUsername("admin3@gmail.com").get().getEnterPrise())
                    .logo(
                            MediaImage.builder()
                                    .name("team3_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/team/team3.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            teamRepository.save(team3);

            TeamLeader teamLeader4 = TeamLeader.builder()
                    .uuid(UUID.randomUUID())
                    .name("Matias")
                    .surname("Snaiders")
                    .username("matiass@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE_SECOND))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.TEAM_LEADER).get()))
                    .mediaImage(
                            MediaImage.builder()
                                    .name("teamleader4_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/teamleader/team_leader3.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            Team team4 = Team.builder()
                    .uuid(UUID.randomUUID())
                    .name("UTN DEVS")
                    .teamLeader(teamLeader4)
                    .descriptionProject("Equipo de desarrolladores utenianos")
                    .regularUsers(Collections.emptyList())
                    .enterPrise(adminRepository.findByUsername("gustavos@gmail.com").get().getEnterPrise())
                    .logo(
                            MediaImage.builder()
                                    .name("team4_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/team/team4.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            teamRepository.save(team4);

            TeamLeader teamLeader5 = TeamLeader.builder()
                    .uuid(UUID.randomUUID())
                    .name("Cesar")
                    .surname("Acuña")
                    .username("cesartl@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE_SECOND))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.TEAM_LEADER).get()))
                    .mediaImage(
                            MediaImage.builder()
                                    .name("cesartl_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/teamleader/cesartl.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            Team team5 = Team.builder()
                    .uuid(UUID.randomUUID())
                    .name("Equipo Feel Flow")
                    .teamLeader(teamLeader5)
                    .descriptionProject("Este equipo es el mejor que jamas vi")
                    .regularUsers(Collections.emptyList())
                    .enterPrise(adminRepository.findByUsername("juanpablo@gmail.com").get().getEnterPrise())
                    .logo(
                            MediaImage.builder()
                                    .name("team5_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/team/team5.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            teamRepository.save(team5);


        }
    }

    private void loadAdminAndEnterprises() throws IOException {
        if(adminRepository.count() < 5){
            Admin admin1 = Admin.builder()
                    .uuid(UUID.randomUUID())
                    .name("admin1")
                    .surname("admin1")
                    .username("admin1@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.ADMIN).get()))
                    .mediaImage(
                            MediaImage.builder()
                                    .name("admin1_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/admin/admin1.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            EnterPrise enterPrise1 = EnterPrise.builder()
                    .uuid(UUID.randomUUID())
                    .name("EmpresaAdmin1")
                    .team(Collections.emptyList())
                    .admin(admin1)
                    .logo(
                            MediaImage.builder()
                                    .name("enterprise1_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/enterprise/enterprise1.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
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
                    .mediaImage(
                            MediaImage.builder()
                                    .name("admin2_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/admin/admin2.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            EnterPrise enterPrise2 = EnterPrise.builder()
                    .uuid(UUID.randomUUID())
                    .name("EmpresaAdmin2")
                    .team(Collections.emptyList())
                    .admin(admin2)
                    .logo(
                            MediaImage.builder()
                                    .name("enterprise2_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/enterprise/enterprise2.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
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
                    .mediaImage(
                            MediaImage.builder()
                                    .name("admin3_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/admin/admin3.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            EnterPrise enterPrise3 = EnterPrise.builder()
                    .uuid(UUID.randomUUID())
                    .name("EmpresaAdmin3")
                    .team(Collections.emptyList())
                    .admin(admin3)
                    .logo(
                            MediaImage.builder()
                                    .name("enterprise3_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/enterprise/enterprise3.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            admin3.setEnterPrise(enterPrise3);
            adminRepository.save(admin3);

            Admin admin4 = Admin.builder()
                    .uuid(UUID.randomUUID())
                    .name("Gustavo")
                    .surname("Santaolalla")
                    .username("gustavos@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE_SECOND))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.ADMIN).get()))
                    .mediaImage(
                            MediaImage.builder()
                                    .name("admin4_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/admin/admin3.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            EnterPrise enterPrise4 = EnterPrise.builder()
                    .uuid(UUID.randomUUID())
                    .name("UTN SOLUTIONS")
                    .team(Collections.emptyList())
                    .admin(admin4)
                    .logo(
                            MediaImage.builder()
                                    .name("enterprise4_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/enterprise/enterprise4.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            admin4.setEnterPrise(enterPrise4);
            adminRepository.save(admin4);

            Admin admin5 = Admin.builder()
                    .uuid(UUID.randomUUID())
                    .name("Juan Pablo")
                    .surname("Zozaya")
                    .username("juanpablo@gmail.com")
                    .password(passwordEncoder.encode(PASSWORD_TEMPLATE_SECOND))
                    .authorities(List.of(authorityRepository.findAuthorityByTeamRoles(TeamRoles.ADMIN).get()))
                    .mediaImage(
                            MediaImage.builder()
                                    .name("juanpablo_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/profile/admin/juanpablo.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            EnterPrise enterPrise5 = EnterPrise.builder()
                    .uuid(UUID.randomUUID())
                    .name("Equipo Feel Flow")
                    .team(Collections.emptyList())
                    .admin(admin5)
                    .logo(
                            MediaImage.builder()
                                    .name("feelflow_image")
                                    .fileType("image/jpg")
                                    .fileData(
                                            convertToBase64( new ClassPathResource(("images/enterprise/feelflow.jpg")).getInputStream() )
                                    )
                                    .build()
                    )
                    .build();

            admin5.setEnterPrise(enterPrise5);
            adminRepository.save(admin5);

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
