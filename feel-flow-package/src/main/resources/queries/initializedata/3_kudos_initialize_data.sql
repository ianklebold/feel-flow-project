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
-- Se crea un tablero para cada RegularUser (excluyendo team leader y admin)
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
-- Se asignan badges a otros usuarios (destinatarios) usando el tablero de un usuario (el "giver").
-- Se usan subconsultas para obtener los identificadores.
-- Nota: Los badges de tipo MANOS_AMIGAS se asignan mediante la tabla intermedia.
-- =============================================================

/* TEAM1, módulo 30:
   Supongamos que los miembros de team1 son:
     - member1@gmail.com
     - member2@gmail.com
     - member3@gmail.com
   Usaremos los tableros de cada uno para otorgar badges a otro.

   Por ejemplo:
   - El tablero de member1 (giver) se usará para otorgar:
         • ENERGIA_POSITIVA, RESOLUTOR_ESTRELLA y MAESTRO_DEL_DETALLE a member2 (recipient)
         • Y 1 badge de MANOS_AMIGAS a member3.
   - El tablero de member2 (giver) se usará para otorgar:
         • ENERGIA_POSITIVA a member3.
   - El tablero de member3 (giver) se usará para no otorgar nada.
*/

-- TEAM1, módulo 30, para el tablero de member1:
-- Insertar badges únicos para el recipient member2:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 2, (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com'));
SET @b1 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 1, (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com'));
SET @b2 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 3, (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com'));
SET @b3 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b1,
    badge_resolutor_star_id = @b2,
    master_of_detail_id = @b3
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member1@gmail.com')
  AND kudos_module_kudosmodule_module = 30;
-- Asignar 1 badge de MANOS_AMIGAS (tipo 0) desde el tablero de member1 al recipient member3:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 0, (SELECT uuid FROM user_model WHERE username = 'member3@gmail.com'));
SET @bf1 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member1@gmail.com')
              AND kudos_module_kudosmodule_module = 30),
           @bf1
       );

-- TEAM1, módulo 30, para el tablero de member2:
-- Otorgar ENERGIA_POSITIVA a recipient member3:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-07-31', 2, (SELECT uuid FROM user_model WHERE username = 'member3@gmail.com'));
SET @b4 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b4
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com')
  AND kudos_module_kudosmodule_module = 30;

-- El tablero de member3 no otorga ningún badge.

-------------------------------------------------------------
/* TEAM2, módulo 31:
   Supongamos que los miembros de team2 son:
     - member4@gmail.com, member5@gmail.com, member6@gmail.com.
   Usaremos:
   - El tablero de member4 (giver) para otorgar:
         • MAESTRO_DEL_DETALLE (3) a recipient member5.
         • Y 1 badge de MANOS_AMIGAS (0) a recipient member6.
   - El tablero de member5 (giver) para otorgar:
         • ENERGIA_POSITIVA (2) a recipient member4.
   - El tablero de member6 (giver) no otorga badges.
*/
-- TEAM2, módulo 31, tablero de member4:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-02-28', 3, (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'));
SET @b5 = LAST_INSERT_ID();
UPDATE table_badge
SET master_of_detail_id = @b5
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com')
  AND kudos_module_kudosmodule_module = 31;
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-02-28', 0, (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'));
SET @bf2 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com')
              AND kudos_module_kudosmodule_module = 31),
           @bf2
       );

-- TEAM2, módulo 31, tablero de member5:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-02-28', 2, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @b6 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b6
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com')
  AND kudos_module_kudosmodule_module = 31;

-- El tablero de member6 no otorga badges.

-------------------------------------------------------------
/* TEAM2, módulo 32:
   Para team2, seguimos con los mismos miembros.
   - El tablero de member4 (giver) otorgará:
         • RESOLUTOR_ESTRELLA (1) y 2 badges de MANOS_AMIGAS al recipient member5.
   - El tablero de member5 (giver) otorgará:
         • ENERGIA_POSITIVA (2) y 1 badge de MANOS_AMIGAS al recipient member4.
   - El tablero de member6 (giver) otorgará todos los badges (RESOLUTOR_ESTRELLA, ENERGIA_POSITIVA, MAESTRO_DEL_DETALLE) al recipient member4,
         y además 2 badges de MANOS_AMIGAS al recipient member5.
*/
-- TEAM2, módulo 32, tablero de member4:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 1, (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'));
SET @b7 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_resolutor_star_id = @b7
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com')
  AND kudos_module_kudosmodule_module = 32;
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'));
SET @bf3 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf3
       );
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'));
SET @bf4 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf4
       );

-- TEAM2, módulo 32, tablero de member5:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 2, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @b8 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b8
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com')
  AND kudos_module_kudosmodule_module = 32;
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @bf5 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf5
       );

-- TEAM2, módulo 32, tablero de member6:
-- Otorgar todos los badges a recipient member4 y 2 de MANOS_AMIGAS al recipient member5.
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 3, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @b9 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 1, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @b10 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 2, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @b11 = LAST_INSERT_ID();
UPDATE table_badge
SET master_of_detail_id = @b9,
    badge_resolutor_star_id = @b10,
    badge_positive_energy_id = @b11
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com')
  AND kudos_module_kudosmodule_module = 32;
-- Asignar 2 badges de MANOS_AMIGAS para member6:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @bf6 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf6
       );
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-08-31', 0, (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'));
SET @bf7 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com')
              AND kudos_module_kudosmodule_module = 32),
           @bf7
       );

-------------------------------------------------------------
/* TEAM2, módulo 13:
   Para team2, módulo 13, asignamos:
   - Para el tablero de member4: ningún badge.
   - Para el tablero de member5: asigna MAESTRO_DEL_DETALLE (3) y ENERGIA_POSITIVA (2) al recipient member6.
   - Para el tablero de member6: asigna RESOLUTOR_ESTRELLA (1) al recipient member5.
*/
-- TEAM2, módulo 13, tablero de member5:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-04-30', 3, (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'));
SET @b12 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-04-30', 2, (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'));
SET @b13 = LAST_INSERT_ID();
UPDATE table_badge
SET master_of_detail_id = @b12,
    badge_positive_energy_id = @b13
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com')
  AND kudos_module_kudosmodule_module = 13;
-- TEAM2, módulo 13, tablero de member6:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-04-30', 1, (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'));
SET @b14 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_resolutor_star_id = @b14
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com')
  AND kudos_module_kudosmodule_module = 13;

-------------------------------------------------------------
/* TEAM3, módulo 33:
   Supongamos que los miembros de team3 son: member7, member8, member9.
   - Para el tablero de member7: asigna ENERGIA_POSITIVA, RESOLUTOR_ESTRELLA y MAESTRO_DEL_DETALLE al recipient member8, y 2 badges de MANOS_AMIGAS al recipient member9.
   - Para el tablero de member8: asigna ENERGIA_POSITIVA al recipient member7.
   - Para el tablero de member9: no asigna nada.
*/
-- TEAM3, módulo 33, tablero de member7:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 2, (SELECT uuid FROM user_model WHERE username = 'member8@gmail.com'));
SET @b15 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 1, (SELECT uuid FROM user_model WHERE username = 'member8@gmail.com'));
SET @b16 = LAST_INSERT_ID();
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 3, (SELECT uuid FROM user_model WHERE username = 'member8@gmail.com'));
SET @b17 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b15,
    badge_resolutor_star_id = @b16,
    master_of_detail_id = @b17
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com')
  AND kudos_module_kudosmodule_module = 33;
-- Asignar 2 badges de MANOS_AMIGAS para el tablero de member7, otorgados a member9:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 0, (SELECT uuid FROM user_model WHERE username = 'member9@gmail.com'));
SET @bf8 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com')
              AND kudos_module_kudosmodule_module = 33),
           @bf8
       );
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 0, (SELECT uuid FROM user_model WHERE username = 'member9@gmail.com'));
SET @bf9 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
           (SELECT id FROM table_badge
            WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com')
              AND kudos_module_kudosmodule_module = 33),
           @bf9
       );

-- TEAM3, módulo 33, tablero de member8:
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-09-30', 2, (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com'));
SET @b18 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b18
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'member8@gmail.com')
  AND kudos_module_kudosmodule_module = 33;

-- El tablero de member9 no otorga badges.