-- =============================================================
-- 0. Variables de entorno (IDs continúan tras los usados antes)
-- =============================================================
SET @team5           = _utf8mb4'Equipo Feel Flow' COLLATE utf8mb4_unicode_ci;
SET @mod1            = 3;    -- siguientes dos módulos
SET @mod2            = 4;
SET @survey1_base    = 7;    -- encuestas 7,8,9 → módulo 3
SET @survey2_base    = 10;   -- encuestas 10,11,12 → módulo 4
SET @c1_pub          = '2025-06-01 08:00:00';
SET @c1_close        = '2025-06-05 18:00:00';
SET @c2_pub          = '2025-06-06 08:00:00';
SET @c2_close        = '2025-06-10 18:00:00';

-- =============================================================
-- 1. MÓDULOS FINISHED de TWELVE_STEPS para Equipo Feel Flow
-- =============================================================
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (@mod1, '2025-06-01', @c1_pub, @c1_close, '2025-06-05', 'FINISHED', 'TWELVE_STEPS',
     (SELECT uuid FROM team WHERE name = @team5)),
    (@mod2, '2025-06-06', @c2_pub, @c2_close, '2025-06-10', 'FINISHED', 'TWELVE_STEPS',
     (SELECT uuid FROM team WHERE name = @team5));

-- =============================================================
-- 2. survey_module + twelve_steps_module
-- =============================================================
INSERT INTO survey_module (surveymodule_module)
VALUES (@mod1), (@mod2);

INSERT INTO twelve_steps_module (twelvesteps_module)
VALUES (@mod1), (@mod2);

-- =============================================================
-- 3. ENCUESTAS para los 3 miembros de Equipo Feel Flow
--    – ids 7,8,9 en módulo 3
--    – ids 10,11,12 en módulo 4
-- =============================================================
INSERT INTO survey
(id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (
        @survey1_base + 0,
        DATE(@c1_close),
        'FINISHED',
        (SELECT uuid FROM user_model WHERE username = 'graciela@gmail.com'),
        @mod1
    ),
    (
        @survey1_base + 1,
        DATE(@c1_close),
        'FINISHED',
        (SELECT uuid FROM user_model WHERE username = 'ianf@gmail.com'),
        @mod1
    ),
    (
        @survey1_base + 2,
        DATE(@c1_close),
        'FINISHED',
        (SELECT uuid FROM user_model WHERE username = 'jorgeh@gmail.com'),
        @mod1
    );

INSERT INTO survey
(id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (
        @survey2_base + 0,
        DATE(@c2_close),
        'FINISHED',
        (SELECT uuid FROM user_model WHERE username = 'graciela@gmail.com'),
        @mod2
    ),
    (
        @survey2_base + 1,
        DATE(@c2_close),
        'FINISHED',
        (SELECT uuid FROM user_model WHERE username = 'ianf@gmail.com'),
        @mod2
    ),
    (
        @survey2_base + 2,
        DATE(@c2_close),
        'FINISHED',
        (SELECT uuid FROM user_model WHERE username = 'jorgeh@gmail.com'),
        @mod2
    );

-- =============================================================
-- 4. PROCEDIMIENTO PARA INSERTAR 12 ACTIVIDADES “BUENAS” (1.*,2.*,3.*) por encuesta
--    – IDs de actividad continúan desde 73..144
-- =============================================================
SET @nextActivityId = 73;

DELIMITER //
CREATE PROCEDURE InsertPositiveActivities(svid INT, cdate DATETIME)
BEGIN
  DECLARE i   INT DEFAULT 1;
  DECLARE q   TEXT;
  DECLARE ans TEXT;
  WHILE i <= 12 DO
    SET q = ELT(i,
      '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?',
      '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?',
      '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?',
      '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?',
      '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?',
      '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?',
      '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?',
      '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?',
      '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?',
      '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?',
      '¿Sientes que la gratitud se expresa regularmente en el equipo?',
      '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?'
    );
    SET ans = ELT(i,
      '1. Entiendo mi contribución',
      '2. Claro pero a veces tengo dudas',
      '3. Algo confuso',
      '1. Siempre',
      '2. Casi siempre',
      '3. A veces puedo decidir',
      '1. Sí, siempre.',
      '2. Hay apoyo suficiente',
      '3. A veces siento que me faltan oportunidades',
      '1. Sí, siempre',
      '2. Generalmente, si existe',
      '3. A veces hay conexión'
    );

INSERT INTO activity
(id, activity_state, answer, close_date, question)
VALUES
    (@nextActivityId, 'FINISHED', ans, cdate, q);

INSERT INTO survey_activities
(survey_id, activities_id)
VALUES
    (svid, @nextActivityId);

SET @nextActivityId = @nextActivityId + 1;
    SET i = i + 1;
END WHILE;
END;
//
DELIMITER ;

-- Llamamos al procedimiento para cada encuesta de Equipo Feel Flow
CALL InsertPositiveActivities(7,  @c1_close);
CALL InsertPositiveActivities(8,  @c1_close);
CALL InsertPositiveActivities(9,  @c1_close);
CALL InsertPositiveActivities(10, @c2_close);
CALL InsertPositiveActivities(11, @c2_close);
CALL InsertPositiveActivities(12, @c2_close);