-- =============================================================
-- A. INSERCIÓN DE MÓDULOS (tabla module) para el módulo Kudos (todos FINISHED)
-- =============================================================

-- Módulo Kudos para team1 (ID = 30), período: 2025-07-01 a 2025-07-31
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (30, '2025-07-01', '2025-07-01 08:00:00.000000', '2025-07-31 18:00:00.000000', '2025-07-31', 'FINISHED', 'KUDOS',
     (SELECT uuid FROM team WHERE name = 'team1'));

-- Módulo Kudos para team2 (ID = 31), período: 2025-07-01 a 2025-07-31
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (31, '2025-07-01', '2025-07-01 08:00:00.000000', '2025-07-31 18:00:00.000000', '2025-07-31', 'FINISHED', 'KUDOS',
     (SELECT uuid FROM team WHERE name = 'team2'));

-- Otro módulo Kudos para team2 (ID = 32), período: 2025-08-01 a 2025-08-31
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (32, '2025-08-01', '2025-08-01 08:00:00.000000', '2025-08-31 18:00:00.000000', '2025-08-31', 'FINISHED', 'KUDOS',
     (SELECT uuid FROM team WHERE name = 'team2'));

-- Módulo Kudos para team3 (ID = 33), período: 2025-09-01 a 2025-09-30
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (33, '2025-09-01', '2025-09-01 08:00:00.000000', '2025-09-30 18:00:00.000000', '2025-09-30', 'FINISHED', 'KUDOS',
     (SELECT uuid FROM team WHERE name = 'team3'));

-- =============================================================
-- B. INSERCIÓN EN kudos_module (tabla kudos_module)
-- =============================================================
INSERT INTO kudos_module (kudosmodule_module) VALUES (30);
INSERT INTO kudos_module (kudosmodule_module) VALUES (31);
INSERT INTO kudos_module (kudosmodule_module) VALUES (32);
INSERT INTO kudos_module (kudosmodule_module) VALUES (33);

-- =============================================================
-- C. CREAR TABLEROS DE BADGE (tabla table_badge)
-- Para cada módulo se crean tableros para cada RegularUser (excluyendo team leader y admin)
-- =============================================================
-- Para team1, módulo 30:
INSERT INTO table_badge (kudos_module_kudosmodule_module, table_badge_owner_regularuser_persona)
SELECT 30, regularuser_persona
FROM regular_user
WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1');

-- Para team2, módulo 31:
INSERT INTO table_badge (kudos_module_kudosmodule_module, table_badge_owner_regularuser_persona)
SELECT 31, regularuser_persona
FROM regular_user
WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team2');

-- Para team2, módulo 32:
INSERT INTO table_badge (kudos_module_kudosmodule_module, table_badge_owner_regularuser_persona)
SELECT 32, regularuser_persona
FROM regular_user
WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team2');

-- Para team3, módulo 33:
INSERT INTO table_badge (kudos_module_kudosmodule_module, table_badge_owner_regularuser_persona)
SELECT 33, regularuser_persona
FROM regular_user
WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team3');

-- =============================================================
-- D. ASIGNACIÓN DE BADGES Y ACTUALIZACIÓN DE TABLEROS
-- Se asignan badges a los tableros; para badges únicos se usa UPDATE y para MANOS_AMIGAS se insertan en la tabla intermedia.
-- =============================================================

/* TEAM1, módulo 30:
   - Member1 (member1@gmail.com): asigna ENERGIA_POSITIVA (2), RESOLUTOR_ESTRELLA (1) y MAESTRO_DEL_DETALLE (3);
     además, le asigna 1 badge de MANOS_AMIGAS.
*/
-- Asignación de badges únicos para member1:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 2, (SELECT uuid FROM user_model WHERE username = 'member1@gmail.com'));
SET @b1 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 1, (SELECT uuid FROM user_model WHERE username = 'member1@gmail.com'));
SET @b2 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 3, (SELECT uuid FROM user_model WHERE username = 'member1@gmail.com'));
SET @b3 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b1,
    badge_resolutor_star_id = @b2,
    master_of_detail_id = @b3
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member1@gmail.com')
  AND kudos_module_kudosmodule_module = 30;
-- Asignación de badge de MANOS_AMIGAS para member1:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 0, (SELECT uuid FROM user_model WHERE username = 'member1@gmail.com'));
SET @bf1 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member1@gmail.com')
              AND kudos_module_kudosmodule_module = 30),
           @bf1
       );

-- Para member2 (member2@gmail.com): asigna ENERGIA_POSITIVA (2) y RESOLUTOR_ESTRELLA (1);
-- además, asigna 2 badges de MANOS_AMIGAS.
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 2, (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com'));
SET @b4 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 1, (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com'));
SET @b5 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b4,
    badge_resolutor_star_id = @b5
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com')
  AND kudos_module_kudosmodule_module = 30;
-- Asignar 2 badges de MANOS_AMIGAS para member2:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 0, (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com'));
SET @bf2 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com')
              AND kudos_module_kudosmodule_module = 30),
           @bf2
       );
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 0, (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com'));
SET @bf3 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com')
              AND kudos_module_kudosmodule_module = 30),
           @bf3
       );

-- Para member3 (member3@gmail.com): no asigna badges (se dejan todos null).

/* TEAM2, módulo 31:
   - Member4 (member4@gmail.com): asigna MAESTRO_DEL_DETALLE (3) y 1 badge de MANOS_AMIGAS.
*/
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-02-28', 3, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @b6 = LAST_INSERT_ID();
UPDATE table_badge
SET master_of_detail_id = @b6
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com')
  AND kudos_module_kudosmodule_module = 31;
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-02-28', 0, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @bf4 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com')
              AND kudos_module_kudosmodule_module = 31),
           @bf4
       );

-- - Member5 (member5@gmail.com): asigna ENERGIA_POSITIVA (2) únicamente.
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-02-28', 2, (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'));
SET @b7 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b7
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com')
  AND kudos_module_kudosmodule_module = 31;
-- - Member6 (member6@gmail.com): no asigna ningún badge.

/* TEAM2, módulo 32:
   - Member4 (member4@gmail.com): asigna RESOLUTOR_ESTRELLA (1) y 2 badges de MANOS_AMIGAS.
*/
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 1, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @b8 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_resolutor_star_id = @b8
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com')
  AND kudos_module_kudosmodule_module = 32;
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @bf5 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf5
       );
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @bf6 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf6
       );

-- - Member5 (member5@gmail.com): asigna ENERGIA_POSITIVA (2) y 1 badge de MANOS_AMIGAS.
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 2, (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'));
SET @b9 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b9
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com')
  AND kudos_module_kudosmodule_module = 32;
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'));
SET @bf7 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf7
       );

-- - Member6 (member6@gmail.com): asigna MAESTRO_DEL_DETALLE (3), RESOLUTOR_ESTRELLA (1) y ENERGIA_POSITIVA (2);
--   además, se asignan 3 badges de MANOS_AMIGAS.
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 3, (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'));
SET @b10 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 1, (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'));
SET @b11 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 2, (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'));
SET @b12 = LAST_INSERT_ID();
UPDATE table_badge
SET master_of_detail_id = @b10,
    badge_resolutor_star_id = @b11,
    badge_positive_energy_id = @b12
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com')
  AND kudos_module_kudosmodule_module = 32;
-- Asignar 3 badges de MANOS_AMIGAS para member6:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'));
SET @bf8 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf8
       );
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'));
SET @bf9 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf9
       );
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'));
SET @bf10 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf10
       );

-- TEAM3, módulo 33:
-- Member7 (member7@gmail.com): asigna ENERGIA_POSITIVA (2), RESOLUTOR_ESTRELLA (1), MAESTRO_DEL_DETALLE (3) y 2 badges de MANOS_AMIGAS.
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 2, (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com'));
SET @b13 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 1, (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com'));
SET @b14 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 3, (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com'));
SET @b15 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b13,
    badge_resolutor_star_id = @b14,
    master_of_detail_id = @b15
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com')
  AND kudos_module_kudosmodule_module = 33;
-- Asignar 2 badges de MANOS_AMIGAS para member7:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 0, (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com'));
SET @bf11 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com')
              AND kudos_module_kudosmodule_module = 33),
           @bf11
       );
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 0, (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com'));
SET @bf12 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com')
              AND kudos_module_kudosmodule_module = 33),
           @bf12
       );

-- Member8 (member8@gmail.com): asigna ENERGIA_POSITIVA (2) únicamente; no se asigna MANOS_AMIGAS.
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 2, (SELECT uuid FROM user_model WHERE username = 'member8@gmail.com'));
SET @b16 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b16
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member8@gmail.com')
  AND kudos_module_kudosmodule_module = 33;

-- Member9 (member9@gmail.com): asigna RESOLUTOR_ESTRELLA (1) únicamente y 1 badge de MANOS_AMIGAS.
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 1, (SELECT uuid FROM user_model WHERE username = 'member9@gmail.com'));
SET @b17 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_resolutor_star_id = @b17
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member9@gmail.com')
  AND kudos_module_kudosmodule_module = 33;
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 0, (SELECT uuid FROM user_model WHERE username = 'member9@gmail.com'));
SET @bf13 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member9@gmail.com')
              AND kudos_module_kudosmodule_module = 33),
           @bf13
       );