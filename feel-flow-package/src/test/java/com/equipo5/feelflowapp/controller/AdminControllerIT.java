package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.constants.validation.admin.ValidationAdminMessages;
import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.dto.enterprise.EnterpriseDTO;
import com.equipo5.feelflowapp.dto.users.admin.AdminDTO;
import com.equipo5.feelflowapp.repository.users.admin.AdminRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//Tomamos en contexto todos los beans
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
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

    /*

    Verificar se emita un mensaje de error cuando ingresamos mal contrseña en el campo “Contraseña”.
    Verificar que se emita un mensaje de error cuando se dejan campos obligatorios vaciós
    Verificar que se emita mensaje de error cuando no se seleccione la casilla de “Terminos y Conficiones”
     */

    @Nested
    @DisplayName("Clase Registro de admin - Registro exitosa")
    class testCreacionDeAdminCorrecta{
        //C.P 1 : Verificar el registro exitoso del usuario
        @Rollback //Estas dos anotaciones son necesrias, para que se restablezca la BD para los demas tests
        @Transactional
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

    @Nested
    @DisplayName("Clase Registro de admin - email incorrecto")
    class testEmailAdminIncorrecto{
        //C.P 2 : Verificar se emita un mensaje de error cuando ingresamos mal el correo electónico en el campo “Usuario”.
        @Test
        void when_the_email_is_incorrect_we_get_an_error_message_the_admin_is_not_created() throws Exception {
            EnterpriseDTO enterpriseDTO = EnterpriseDTO.builder()
                    .name("Accenture")
                    .build();

            AdminDTO adminDTO = AdminDTO.builder()
                    .name("Cristina")
                    .surname("Torres")
                    .username("cristina.torres.accenture.com")
                    .password("A12346789l@")
                    .enterpriseDTO(enterpriseDTO)
                    .build();

            Gson gson = new Gson();
            String json = gson.toJson(adminDTO);

            mockMvc.perform(post(AdminController.ADMIN_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$[0].username").value(ValidationAdminMessages.VALIDATION_MESSAGE_NOT_WELL_FORMED_EMAIL));
        }
    }

    @Nested
    @DisplayName("Clase Registro de admin - contrasena incorrecto")
    class testPasswordAdminIncorrecto{
        //C.P3 : Verificar se emita un mensaje de error cuando ingresamos mal contrseña en el campo “Contraseña”.
        @Test
        void when_the_password_is_incorrect_we_get_an_error_message_the_admin_is_not_created() throws Exception {
            EnterpriseDTO enterpriseDTO = EnterpriseDTO.builder()
                    .name("Accenture")
                    .build();

            AdminDTO adminDTO = AdminDTO.builder()
                    .name("Cristina")
                    .surname("Torres")
                    .username("cristina.torres@accenture.com")
                    .password("ABC167855")
                    .enterpriseDTO(enterpriseDTO)
                    .build();

            Gson gson = new Gson();
            String json = gson.toJson(adminDTO);

            mockMvc.perform(post(AdminController.ADMIN_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$[0].password").value(ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_WELL_FORMED_PASSWORD));
        }

        @Test
        void when_the_data_is_incorrect_we_get_an_error_message_the_admin_is_not_created() throws Exception {
            EnterpriseDTO enterpriseDTO = EnterpriseDTO.builder()
                    .name(" ")
                    .build();

            AdminDTO adminDTO = AdminDTO.builder()
                    .name(" ")
                    .username("cristina.torres@accenture.com")
                    .password("ABC167855")
                    .enterpriseDTO(enterpriseDTO)
                    .build();

            Gson gson = new Gson();
            String json = gson.toJson(adminDTO);

            MvcResult mvcResult = mockMvc.perform(post(AdminController.ADMIN_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            List<String> content = List.of(
                    new JSONArray(mvcResult.getResponse().getContentAsString(Charset.defaultCharset())).get(0).toString(),
                    new JSONArray(mvcResult.getResponse().getContentAsString(Charset.defaultCharset())).get(1).toString(),
                    new JSONArray(mvcResult.getResponse().getContentAsString(Charset.defaultCharset())).get(2).toString(),
                    new JSONArray(mvcResult.getResponse().getContentAsString(Charset.defaultCharset())).get(3).toString(),
                    new JSONArray(mvcResult.getResponse().getContentAsString(Charset.defaultCharset())).get(4).toString()
            );

            assertThat(content).isNotEmpty();
        }



    }


}
