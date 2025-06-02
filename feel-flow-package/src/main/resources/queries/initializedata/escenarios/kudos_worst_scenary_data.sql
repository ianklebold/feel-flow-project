-- =============================================================
-- Variables de entorno (para no pisar IDs de scripts anteriores)
-- =============================================================
SET @team4  = _utf8mb4'UTN DEVS' COLLATE utf8mb4_unicode_ci;
SET @modA      = 17;  -- primer módulo Kudos para UTN DEVS
SET @modB      = 18;  -- segundo módulo Kudos para UTN DEVS

-- =============================================================
-- A. INSERCIÓN DE MÓDULOS (tabla module) para Kudos (todos FINISHED)
-- =============================================================
-- Módulo Kudos para UTN DEVS (ID = 34), período: 2025-10-01 a 2025-10-31
INSERT INTO module
  (id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
  (@modA,
   '2025-10-01',
   '2025-10-01 08:00:00.000000',
   '2025-10-31 18:00:00.000000',
   '2025-10-31',
   'FINISHED',
   'KUDOS',
   (SELECT uuid FROM team WHERE name = @team4)
  );

-- Módulo Kudos para UTN DEVS (ID = 35), período: 2025-11-01 a 2025-11-30
INSERT INTO module
  (id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
  (@modB,
   '2025-11-01',
   '2025-11-01 08:00:00.000000',
   '2025-11-30 18:00:00.000000',
   '2025-11-30',
   'FINISHED',
   'KUDOS',
   (SELECT uuid FROM team WHERE name = @team4)
  );

-- =============================================================
-- B. INSERCIÓN EN kudos_module
-- =============================================================
INSERT INTO kudos_module (kudosmodule_module) VALUES (@modA), (@modB);

-- =============================================================
-- C. CREAR TABLEROS DE BADGE (table_badge)
--    Un tablero por cada RegularUser de UTN DEVS
-- =============================================================
-- Para módulo 34
INSERT INTO table_badge (kudos_module_kudosmodule_module, table_badge_owner_regularuser_persona)
SELECT @modA, regularuser_persona
FROM regular_user
WHERE team_uuid = (SELECT uuid FROM team WHERE name = @team4);

-- Para módulo 35
INSERT INTO table_badge (kudos_module_kudosmodule_module, table_badge_owner_regularuser_persona)
SELECT @modB, regularuser_persona
FROM regular_user
WHERE team_uuid = (SELECT uuid FROM team WHERE name = @team4);

-- =============================================================
-- D. ASIGNACIÓN DE BADGES (un INSERT por badge, para cada módulo)
-- =============================================================

-- --------------------------
-- MÓDULO 34 (ID = @modA)
-- --------------------------
-- 1) facub@gmail.com otorga a melinat@gmail.com:
--    • ENERGIA_POSITIVA (badge_name = 2)
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-10-31', 2, (SELECT uuid FROM user_model WHERE username = 'melinat@gmail.com'));
SET @b1 = LAST_INSERT_ID();

--    • MAESTRO_DEL_DETALLE (badge_name = 3)
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-10-31', 3, (SELECT uuid FROM user_model WHERE username = 'melinat@gmail.com'));
SET @b2 = LAST_INSERT_ID();

-- Actualizo el tablero de facub con esos dos badges
UPDATE table_badge
SET badge_positive_energy_id = @b1,
    master_of_detail_id      = @b2
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'facub@gmail.com')
  AND kudos_module_kudosmodule_module      = @modA;

--    • MANOS_AMIGAS (badge_name = 0)
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-10-31', 0, (SELECT uuid FROM user_model WHERE username = 'melinat@gmail.com'));
SET @bf1 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
  (SELECT id FROM table_badge
     WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'facub@gmail.com')
       AND kudos_module_kudosmodule_module      = @modA),
  @bf1
);

-- 2) melinat@gmail.com otorga RESOLUTOR_ESTRELLA (badge_name = 1) a facub@gmail.com
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-10-31', 1, (SELECT uuid FROM user_model WHERE username = 'facub@gmail.com'));
SET @b3 = LAST_INSERT_ID();

UPDATE table_badge
SET badge_resolutor_star_id = @b3
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'melinat@gmail.com')
  AND kudos_module_kudosmodule_module      = @modA;

--    renaildo@gmail.com NO recibe ningún badge en este módulo

-- --------------------------
-- MÓDULO 35 (ID = @modB)
-- --------------------------
-- 1) melinat@gmail.com otorga a renaildo@gmail.com:
--    • ENERGIA_POSITIVA (2)
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-11-30', 2, (SELECT uuid FROM user_model WHERE username = 'renaildo@gmail.com'));
SET @b4 = LAST_INSERT_ID();

--    • RESOLUTOR_ESTRELLA (1)
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-11-30', 1, (SELECT uuid FROM user_model WHERE username = 'renaildo@gmail.com'));
SET @b5 = LAST_INSERT_ID();

-- Actualizo el tablero de melinat con ambos badges
UPDATE table_badge
SET badge_positive_energy_id = @b4,
    badge_resolutor_star_id  = @b5
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'melinat@gmail.com')
  AND kudos_module_kudosmodule_module      = @modB;

--    • MANOS_AMIGAS (0)
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-11-30', 0, (SELECT uuid FROM user_model WHERE username = 'renaildo@gmail.com'));
SET @bf2 = LAST_INSERT_ID();
INSERT INTO table_badge_badge_friend_hands (table_badge_id, badge_friend_hands_id)
VALUES (
  (SELECT id FROM table_badge
     WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'melinat@gmail.com')
       AND kudos_module_kudosmodule_module      = @modB),
  @bf2
);

-- 2) renaildo@gmail.com otorga MAESTRO_DEL_DETALLE (3) a melinat@gmail.com
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES ('2025-11-30', 3, (SELECT uuid FROM user_model WHERE username = 'melinat@gmail.com'));
SET @b6 = LAST_INSERT_ID();

UPDATE table_badge
SET master_of_detail_id = @b6
WHERE table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'renaildo@gmail.com')
  AND kudos_module_kudosmodule_module      = @modB;

--    facub@gmail.com NO recibe ningún badge en este módulo