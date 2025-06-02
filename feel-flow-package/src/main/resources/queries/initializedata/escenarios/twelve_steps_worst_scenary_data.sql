-- =============================================================
-- 0. Variables de entorno (base limpia, IDs arrancan en 1)
-- =============================================================
-- USE your_database;  -- apúntalo a tu esquema

SET @team4 = _utf8mb4'UTN DEVS' COLLATE utf8mb4_unicode_ci;
SET @mod1            = 1;
SET @mod2            = 2;
SET @survey1_base    = 1;   -- encuestas 1,2,3 → módulo 1
SET @survey2_base    = 4;   -- encuestas 4,5,6 → módulo 2
SET @c1_pub          = '2025-05-01 08:00:00';
SET @c1_close        = '2025-05-05 18:00:00';
SET @c2_pub          = '2025-05-06 08:00:00';
SET @c2_close        = '2025-05-10 18:00:00';

-- =============================================================
-- 1. MÓDULOS FINISHED de TWELVE_STEPS para UTN DEVS
-- =============================================================
INSERT INTO module
  (id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
  (@mod1, '2025-05-01', @c1_pub, @c1_close, '2025-05-05', 'FINISHED', 'TWELVE_STEPS',
    (SELECT uuid FROM team WHERE name = @team4)),
  (@mod2, '2025-05-06', @c2_pub, @c2_close, '2025-05-10', 'FINISHED', 'TWELVE_STEPS',
    (SELECT uuid FROM team WHERE name = @team4));

-- =============================================================
-- 2. survey_module + twelve_steps_module
-- =============================================================
INSERT INTO survey_module (surveymodule_module)
VALUES (@mod1), (@mod2);

INSERT INTO twelve_steps_module (twelvesteps_module)
VALUES (@mod1), (@mod2);

-- =============================================================
-- 3. ENCUESTAS para los 3 miembros de UTN DEVS
--    – ids 1,2,3 en módulo 1
--    – ids 4,5,6 en módulo 2
-- =============================================================
INSERT INTO survey
  (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
  (
    @survey1_base + 0,
    DATE(@c1_close),
    'FINISHED',
    (SELECT uuid FROM user_model WHERE username = 'facub@gmail.com'),
    @mod1
  ),
  (
    @survey1_base + 1,
    DATE(@c1_close),
    'FINISHED',
    (SELECT uuid FROM user_model WHERE username = 'melinat@gmail.com'),
    @mod1
  ),
  (
    @survey1_base + 2,
    DATE(@c1_close),
    'FINISHED',
    (SELECT uuid FROM user_model WHERE username = 'renaildo@gmail.com'),
    @mod1
  );

INSERT INTO survey
  (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
  (
    @survey2_base + 0,
    DATE(@c2_close),
    'FINISHED',
    (SELECT uuid FROM user_model WHERE username = 'facub@gmail.com'),
    @mod2
  ),
  (
    @survey2_base + 1,
    DATE(@c2_close),
    'FINISHED',
    (SELECT uuid FROM user_model WHERE username = 'melinat@gmail.com'),
    @mod2
  ),
  (
    @survey2_base + 2,
    DATE(@c2_close),
    'FINISHED',
    (SELECT uuid FROM user_model WHERE username = 'renaildo@gmail.com'),
    @mod2
  );

-- =============================================================
-- 4. PROCEDIMIENTO PARA INSERTAR 12 ACTIVIDADES “5.*” por encuesta
--    – IDs de actividad 1..72
-- =============================================================
SET @nextActivityId = 1;

DELIMITER //
CREATE PROCEDURE InsertActivitiesForSurvey(svid INT, cdate DATETIME)
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE q TEXT;
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
      '5. Completamente confuso',
      '5. Nunca tengo autonomía',
      '5. Nunca',
      '5. Nunca',
      '5. Nunca me siento reconocido.',
      '5. Nunca.',
      '5. Nunca me siento seguro',
      '5. Nunca',
      '5. Nunca',
      '5. Nunca se fomenta la concentración',
      '5. Nunca se practica la gratitud',
      '5. Completamente desalineados'
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

-- Llamamos al procedimiento para cada encuesta
CALL InsertActivitiesForSurvey(1, @c1_close);
CALL InsertActivitiesForSurvey(2, @c1_close);
CALL InsertActivitiesForSurvey(3, @c1_close);
CALL InsertActivitiesForSurvey(4, @c2_close);
CALL InsertActivitiesForSurvey(5, @c2_close);
CALL InsertActivitiesForSurvey(6, @c2_close);