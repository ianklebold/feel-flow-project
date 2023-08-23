package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.dto.enterprise.EnterpriseDTO;
import com.equipo5.feelflowapp.dto.users.admin.AdminDTO;
import com.equipo5.feelflowapp.repository.users.admin.AdminRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


//Tomamos en contexto todos los beans
@SpringBootTest
@ActiveProfiles("test")
class AdminControllerIT {
    @Autowired
    AdminController adminController;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext; //Con este buscamos los beans del contexto

    MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        //Inyectamos todos los beans en mockmvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    void when_the_data_admin_is_correct_this_is_created(){
        EnterpriseDTO enterpriseDTO = EnterpriseDTO.builder()
                .name("Accenture")
                .build();

        AdminDTO adminDTO = AdminDTO.builder()
                .name("Cristina")
                .surname("Torres")
                .username("cristina.torres@accenture.com")
                .password("A12346789l@")
                .enterpriseDTO(enterpriseDTO)
                .build();

        ResponseEntity responseEntity = adminController.createAdmin(adminDTO);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Optional<Admin> admin = adminRepository.findById(savedUUID);

        assertThat(admin).isPresent();

        assertThat(admin.get().getName()).isEqualTo("Cristina");
        assertThat(admin.get().getSurname()).isEqualTo("Torres");
        assertThat(admin.get().getUsername()).isEqualTo("cristina.torres@accenture.com");
        assertThat(passwordEncoder.matches("A12346789l@",admin.get().getPassword())).isTrue();
        assertThat(admin.get().getEnterPrise()).isNotNull();
        assertThat(admin.get().getEnterPrise().getName()).isEqualTo("Accenture");
    }

}
