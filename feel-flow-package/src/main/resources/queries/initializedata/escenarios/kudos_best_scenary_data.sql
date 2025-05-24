-- =============================================================
-- Script Kudos para Equipo Feel Flow (team5)
-- Se ejecuta después del script de Niko-Niko y del Kudos de UTN DEVS
-- =============================================================

-- 0. IDs reservados (último módulo usado: 18)
SET @modC = 19;  -- primer módulo Kudos para Equipo Feel Flow
SET @modD = 20;  -- segundo módulo Kudos para Equipo Feel Flow

-- =============================================================
-- A. INSERCIÓN DE MÓDULOS Kudos (todos FINISHED)
-- =============================================================
-- Módulo Kudos 1 para Equipo Feel Flow (ID = 19), período: 2025-12-01 a 2025-12-31
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (
        @modC,
        '2025-12-01',
        '2025-12-01 08:00:00.000000',
        '2025-12-31 18:00:00.000000',
        '2025-12-31',
        'FINISHED',
        'KUDOS',
        (SELECT uuid FROM team WHERE name = 'Equipo Feel Flow')
    );

-- Módulo Kudos 2 para Equipo Feel Flow (ID = 20), período: 2026-01-01 a 2026-01-31
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (
        @modD,
        '2026-01-01',
        '2026-01-01 08:00:00.000000',
        '2026-01-31 18:00:00.000000',
        '2026-01-31',
        'FINISHED',
        'KUDOS',
        (SELECT uuid FROM team WHERE name = 'Equipo Feel Flow')
    );

-- =============================================================
-- B. INSERCIÓN EN kudos_module
-- =============================================================
INSERT INTO kudos_module (kudosmodule_module) VALUES (@modC), (@modD);

-- =============================================================
-- C. CREAR TABLEROS DE BADGE para cada miembro de Equipo Feel Flow
-- =============================================================
-- Módulo 19
INSERT INTO table_badge (kudos_module_kudosmodule_module, table_badge_owner_regularuser_persona)
SELECT @modC, regularuser_persona
FROM regular_user
WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'Equipo Feel Flow');

-- Módulo 20
INSERT INTO table_badge (kudos_module_kudosmodule_module, table_badge_owner_regularuser_persona)
SELECT @modD, regularuser_persona
FROM regular_user
WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'Equipo Feel Flow');

-- =============================================================
-- D. ASIGNACIÓN DE BADGES (todos reciben al menos uno)
--    Tipos: 0=MANOS_AMIGAS, 1=RESOLUTOR_ESTRELLA, 2=ENERGIA_POSITIVA, 3=MAESTRO_DEL_DETALLE
-- =============================================================

-- ==== MÓDULO 19 (cierre: 2025-12-31) ====

-- 1) Tablero de Graciela otorga ENERGIA_POSITIVA a Ian
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES (
           '2025-12-31',
           2,
           (SELECT uuid FROM user_model WHERE username = 'ianf@gmail.com')
       );
SET @b1 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b1
WHERE
    table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'graciela@gmail.com')
  AND kudos_module_kudosmodule_module = @modC;

-- 2) Tablero de Ian otorga RESOLUTOR_ESTRELLA a Jorge
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES (
           '2025-12-31',
           1,
           (SELECT uuid FROM user_model WHERE username = 'jorgeh@gmail.com')
       );
SET @b2 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_resolutor_star_id = @b2
WHERE
    table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'ianf@gmail.com')
  AND kudos_module_kudosmodule_module = @modC;

-- 3) Tablero de Jorge otorga MAESTRO_DEL_DETALLE a Graciela
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES (
           '2025-12-31',
           3,
           (SELECT uuid FROM user_model WHERE username = 'graciela@gmail.com')
       );
SET @b3 = LAST_INSERT_ID();
UPDATE table_badge
SET master_of_detail_id = @b3
WHERE
    table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'jorgeh@gmail.com')
  AND kudos_module_kudosmodule_module = @modC;


-- ==== MÓDULO 20 (cierre: 2026-01-31) ====

-- 1) Tablero de Graciela otorga MAESTRO_DEL_DETALLE a Ian
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES (
           '2026-01-31',
           3,
           (SELECT uuid FROM user_model WHERE username = 'ianf@gmail.com')
       );
SET @b4 = LAST_INSERT_ID();
UPDATE table_badge
SET master_of_detail_id = @b4
WHERE
    table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'graciela@gmail.com')
  AND kudos_module_kudosmodule_module = @modD;

-- 2) Tablero de Ian otorga ENERGIA_POSITIVA a Jorge
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES (
           '2026-01-31',
           2,
           (SELECT uuid FROM user_model WHERE username = 'jorgeh@gmail.com')
       );
SET @b5 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_positive_energy_id = @b5
WHERE
    table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'ianf@gmail.com')
  AND kudos_module_kudosmodule_module = @modD;

-- 3) Tablero de Jorge otorga RESOLUTOR_ESTRELLA a Graciela
INSERT INTO badge (awarded_date, badge_name, badge_owner_regularuser_persona)
VALUES (
           '2026-01-31',
           1,
           (SELECT uuid FROM user_model WHERE username = 'graciela@gmail.com')
       );
SET @b6 = LAST_INSERT_ID();
UPDATE table_badge
SET badge_resolutor_star_id = @b6
WHERE
    table_badge_owner_regularuser_persona = (SELECT uuid FROM user_model WHERE username = 'jorgeh@gmail.com')
  AND kudos_module_kudosmodule_module = @modD;