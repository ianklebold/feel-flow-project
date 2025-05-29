-- =============================================================
-- 1. MÓDULOS (tabla module)
-- =============================================================

-- Módulo ACTIVE para team1
INSERT INTO module
(id, creation_date, date_and_time_to_close, date_and_time_to_publish, module_closed_date, module_state, name, team_uuid)
VALUES
    (1, '2025-03-30', '2025-04-05 18:00:00.000000', '2025-03-30 08:00:00.000000', NULL, 'ACTIVE', 'TWELVE_STEPS',
     (SELECT uuid FROM team WHERE name = 'team1'));

-- Módulo FINISHED para team1
INSERT INTO module
(id, creation_date, date_and_time_to_close, date_and_time_to_publish, module_closed_date, module_state, name, team_uuid)
VALUES
    (2, '2025-03-20', '2025-03-25 18:00:00.000000', '2025-03-20 08:00:00.000000', '2025-03-25', 'FINISHED', 'TWELVE_STEPS',
     (SELECT uuid FROM team WHERE name = 'team1'));

-- Módulo FINISHED para team2
INSERT INTO module
(id, creation_date, date_and_time_to_close, date_and_time_to_publish, module_closed_date, module_state, name, team_uuid)
VALUES
    (3, '2025-03-10', '2025-03-15 18:00:00.000000', '2025-03-10 08:00:00.000000', '2025-03-15', 'FINISHED', 'TWELVE_STEPS',
     (SELECT uuid FROM team WHERE name = 'team2'));

-- Módulo ACTIVE para team3
INSERT INTO module
(id, creation_date, date_and_time_to_close, date_and_time_to_publish, module_closed_date, module_state, name, team_uuid)
VALUES
    (4, '2025-03-30', '2025-04-05 18:00:00.000000', '2025-03-30 08:00:00.000000', NULL, 'ACTIVE', 'TWELVE_STEPS',
     (SELECT uuid FROM team WHERE name = 'team3'));

-- =============================================================
-- 2. SURVEY_MODULE y TWELVE_STEPS_MODULE
-- =============================================================

-- Para módulo ACTIVE de team1 (id = 1)
INSERT INTO survey_module (surveymodule_module)
VALUES ((SELECT id FROM module
         WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1')
           AND module_state = 'ACTIVE' AND name = 'TWELVE_STEPS'
    LIMIT 1));
INSERT INTO twelve_steps_module (twelvesteps_module)
VALUES ((SELECT surveymodule_module FROM survey_module
         WHERE surveymodule_module = (SELECT id FROM module
                                      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1')
                                        AND module_state = 'ACTIVE' AND name = 'TWELVE_STEPS'
    LIMIT 1)
    LIMIT 1));

-- Para módulo FINISHED de team1 (id = 2)
INSERT INTO survey_module (surveymodule_module)
VALUES ((SELECT id FROM module
         WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1')
           AND module_state = 'FINISHED' AND name = 'TWELVE_STEPS'
         ORDER BY id DESC LIMIT 1));
INSERT INTO twelve_steps_module (twelvesteps_module)
VALUES ((SELECT surveymodule_module FROM survey_module
         WHERE surveymodule_module = (SELECT id FROM module
                                      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1')
                                        AND module_state = 'FINISHED' AND name = 'TWELVE_STEPS'
                                      ORDER BY id DESC LIMIT 1)
    LIMIT 1));

-- Para módulo FINISHED de team2 (id = 3)
INSERT INTO survey_module (surveymodule_module)
VALUES ((SELECT id FROM module
         WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team2')
           AND module_state = 'FINISHED' AND name = 'TWELVE_STEPS'
    LIMIT 1));
INSERT INTO twelve_steps_module (twelvesteps_module)
VALUES ((SELECT surveymodule_module FROM survey_module
         WHERE surveymodule_module = (SELECT id FROM module
                                      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team2')
                                        AND module_state = 'FINISHED' AND name = 'TWELVE_STEPS'
    LIMIT 1)
    LIMIT 1));

-- Para módulo ACTIVE de team3 (id = 4)
INSERT INTO survey_module (surveymodule_module)
VALUES ((SELECT id FROM module
         WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team3')
           AND module_state = 'ACTIVE' AND name = 'TWELVE_STEPS'
    LIMIT 1));
INSERT INTO twelve_steps_module (twelvesteps_module)
VALUES ((SELECT surveymodule_module FROM survey_module
         WHERE surveymodule_module = (SELECT id FROM module
                                      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team3')
                                        AND module_state = 'ACTIVE' AND name = 'TWELVE_STEPS'
    LIMIT 1)
    LIMIT 1));

-- =============================================================
-- 3. ENCUESTAS (tabla survey)
-- Se asignan manualmente los IDs para facilitar la vinculación:
-- TEAM1, módulo ACTIVE (id=1): survey ids 1,2,3
-- TEAM1, módulo FINISHED (id=2): survey ids 4,5,6
-- TEAM2, módulo FINISHED (id=3): survey ids 7,8,9
-- TEAM3, módulo ACTIVE (id=4): survey ids 10,11,12
-- =============================================================

-- TEAM1, módulo ACTIVE
-- Survey id 1 para member1: FINISHED (close_date = '2025-03-30')
INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (1, '2025-03-30', 'FINISHED',
     (SELECT uuid  FROM user_model WHERE username = 'member1@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1')
        AND module_state = 'ACTIVE' AND name = 'TWELVE_STEPS'
         LIMIT 1));

-- Survey id 2 para member2: ACTIVE
INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (2, NULL, 'ACTIVE',
     (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1')
        AND module_state = 'ACTIVE' AND name = 'TWELVE_STEPS'
         LIMIT 1));

-- Survey id 3 para member3: ACTIVE
INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (3, NULL, 'ACTIVE',
     (SELECT uuid FROM user_model WHERE username = 'member3@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1')
        AND module_state = 'ACTIVE' AND name = 'TWELVE_STEPS'
         LIMIT 1));

-- TEAM1, módulo FINISHED
-- Survey id 4 para member1: FINISHED (close_date = '2025-03-25')
INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (4, '2025-03-25', 'FINISHED',
     (SELECT uuid FROM user_model WHERE username = 'member1@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1')
        AND module_state = 'FINISHED' AND name = 'TWELVE_STEPS'
      ORDER BY id DESC LIMIT 1));

INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (5, '2025-03-25', 'FINISHED',
     (SELECT uuid FROM user_model WHERE username = 'member2@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1')
        AND module_state = 'FINISHED' AND name = 'TWELVE_STEPS'
      ORDER BY id DESC LIMIT 1));

INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (6, '2025-03-25', 'FINISHED',
     (SELECT uuid FROM user_model WHERE username = 'member3@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team1')
        AND module_state = 'FINISHED' AND name = 'TWELVE_STEPS'
      ORDER BY id DESC LIMIT 1));

-- TEAM2, módulo FINISHED
-- Survey id 7 para member4, Survey id 8 para member5, Survey id 9 para member6 (close_date = '2025-03-15')
INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (7, '2025-03-15', 'FINISHED',
     (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team2')
        AND module_state = 'FINISHED' AND name = 'TWELVE_STEPS'
         LIMIT 1));

INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (8, '2025-03-15', 'FINISHED',
     (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team2')
        AND module_state = 'FINISHED' AND name = 'TWELVE_STEPS'
         LIMIT 1));

INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (9, '2025-03-15', 'FINISHED',
     (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team2')
        AND module_state = 'FINISHED' AND name = 'TWELVE_STEPS'
         LIMIT 1));

-- TEAM3, módulo ACTIVE
-- Survey id 10 para member7: FINISHED (close_date = '2025-03-30')
INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (10, '2025-03-30', 'FINISHED',
     (SELECT uuid FROM user_model WHERE username = 'member7@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team3')
        AND module_state = 'ACTIVE' AND name = 'TWELVE_STEPS'
         LIMIT 1));

INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (11, NULL, 'ACTIVE',
     (SELECT uuid FROM user_model WHERE username = 'member8@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team3')
        AND module_state = 'ACTIVE' AND name = 'TWELVE_STEPS'
         LIMIT 1));

INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (12, NULL, 'ACTIVE',
     (SELECT uuid FROM user_model WHERE username = 'member9@gmail.com'),
     (SELECT id FROM module
      WHERE team_uuid = (SELECT uuid FROM team WHERE name = 'team3')
        AND module_state = 'ACTIVE' AND name = 'TWELVE_STEPS'
         LIMIT 1));

-- =============================================================
-- 4. ACTIVIDADES Y SURVEY_ACTIVITIES
-- Se insertan 12 actividades para cada encuesta.
-- Las encuestas en estado FINISHED tendrán respuestas variadas.
-- =============================================================

/* TEAM1, módulo ACTIVE:
   Survey id 1 (FINISHED) para member1 */
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            -- Para la pregunta 1, usamos variante 1
                                                                            (1, 'FINISHED', '1. Totalmente claro y entiendo mi contribución', '2025-03-30',
                                                                             (SELECT QUESTIONS_POOL_CLASSIC_TWELVE_STEPS FROM (SELECT '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?' AS QUESTIONS_POOL_CLASSIC_TWELVE_STEPS) AS t)),
                                                                            -- Pregunta 2, variante 2
                                                                            (2, 'FINISHED', '2. Casi siempre tengo autonomía suficiente', '2025-03-30',
                                                                             '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            -- Pregunta 3, variante 3
                                                                            (3, 'FINISHED', '3. A veces siento que me faltan oportunidades', '2025-03-30',
                                                                             '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            -- Pregunta 4, variante 2
                                                                            (4, 'FINISHED', '2. Generalmente, hay un buen nivel de conexión', '2025-03-30',
                                                                             '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            -- Pregunta 5, variante 3
                                                                            (5, 'FINISHED', '3. A veces se reconocen, pero podría ser mejor', '2025-03-30',
                                                                             '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            -- Pregunta 6, variante 2
                                                                            (6, 'FINISHED', '2. Generalmente siento que estoy haciendo una diferencia', '2025-03-30',
                                                                             '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            -- Pregunta 7, variante 2
                                                                            (7, 'FINISHED', '2. Generalmente puedo hablar con confianza', '2025-03-30',
                                                                             '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            -- Pregunta 8, variante 3
                                                                            (8, 'FINISHED', '3. A veces hay momentos de diversión, pero no muchos', '2025-03-30',
                                                                             '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            -- Pregunta 9, variante 2
                                                                            (9, 'FINISHED', '2. Generalmente me siento apoyado', '2025-03-30',
                                                                             '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            -- Pregunta 10, variante 3
                                                                            (10, 'FINISHED', '3. A veces se fomenta, pero no siempre', '2025-03-30',
                                                                             '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            -- Pregunta 11, variante 4
                                                                            (11, 'FINISHED', '4. Rara vez siento gratitud en el equipo', '2025-03-30',
                                                                             '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            -- Pregunta 12, variante 2
                                                                            (12, 'FINISHED', '2. Bastante alineados', '2025-03-30',
                                                                             '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12);

/* TEAM1, módulo FINISHED:
   Survey id 4 para member1 */
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            -- Para Survey id 4, usamos una serie diferente (desplazamos los índices)
                                                                            (37, 'FINISHED', '2. Bastante claro, aunque a veces tengo dudas', '2025-03-25',
                                                                             '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (38, 'FINISHED', '3. A veces puedo decidir, pero no siempre', '2025-03-25',
                                                                             '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (39, 'FINISHED', '4. Rara vez recibo apoyo para mejorar.', '2025-03-25',
                                                                             '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (40, 'FINISHED', '1. Sí, siempre siento una conexión fuerte y apoyo mutuo', '2025-03-25',
                                                                             '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (41, 'FINISHED', '2. Generalmente se reconocen, aunque no siempre.', '2025-03-25',
                                                                             '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (42, 'FINISHED', '3. A veces siento que mi trabajo impacta, pero no siempre.', '2025-03-25',
                                                                             '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (43, 'FINISHED', '1. Siempre me siento seguro para expresarme', '2025-03-25',
                                                                             '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (44, 'FINISHED', '4. Rara vez hay espacio para divertirse', '2025-03-25',
                                                                             '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (45, 'FINISHED', '3. A veces siento apoyo, pero podría ser mejor', '2025-03-25',
                                                                             '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (46, 'FINISHED', '4. Rara vez se promueve la atención plena', '2025-03-25',
                                                                             '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (47, 'FINISHED', '2. Generalmente se expresa gratitud', '2025-03-25',
                                                                             '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (48, 'FINISHED', '3. Algo alineados', '2025-03-25',
                                                                             '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (4, 37), (4, 38), (4, 39), (4, 40), (4, 41), (4, 42), (4, 43), (4, 44), (4, 45), (4, 46), (4, 47), (4, 48);

/* TEAM1, módulo FINISHED:
   Survey id 5 para member2 */
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (49, 'FINISHED', '3. Algo confuso, no siempre lo entiendo', '2025-03-25',
                                                                             '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (50, 'FINISHED', '4. Rara vez puedo tomar decisiones por mi cuenta', '2025-03-25',
                                                                             '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (51, 'FINISHED', '2. En general, hay apoyo suficiente', '2025-03-25',
                                                                             '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (52, 'FINISHED', '2. Generalmente, hay un buen nivel de conexión', '2025-03-25',
                                                                             '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (53, 'FINISHED', '3. A veces se reconocen, pero podría ser mejor', '2025-03-25',
                                                                             '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (54, 'FINISHED', '2. Generalmente siento que estoy haciendo una diferencia', '2025-03-25',
                                                                             '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (55, 'FINISHED', '2. Generalmente puedo hablar con confianza', '2025-03-25',
                                                                             '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (56, 'FINISHED', '3. A veces hay momentos de diversión, pero no muchos', '2025-03-25',
                                                                             '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (57, 'FINISHED', '2. Generalmente me siento apoyado', '2025-03-25',
                                                                             '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (58, 'FINISHED', '3. A veces se fomenta, pero no siempre', '2025-03-25',
                                                                             '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (59, 'FINISHED', '4. Rara vez se promueve la atención plena', '2025-03-25',
                                                                             '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (60, 'FINISHED', '2. Bastante alineados', '2025-03-25',
                                                                             '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (5, 49), (5, 50), (5, 51), (5, 52), (5, 53), (5, 54), (5, 55), (5, 56), (5, 57), (5, 58), (5, 59), (5, 60);

/* TEAM1, módulo FINISHED:
   Survey id 6 para member3 */
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (61, 'FINISHED', '4. Poco claro, rara vez sé cómo contribuyo', '2025-03-25',
                                                                             '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (62, 'FINISHED', '5. Nunca tengo autonomía', '2025-03-25',
                                                                             '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (63, 'FINISHED', '1. Sí, siempre me siento apoyado para mejorar.', '2025-03-25',
                                                                             '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (64, 'FINISHED', '3. A veces hay conexión, pero no siempre', '2025-03-25',
                                                                             '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (65, 'FINISHED', '4. Rara vez siento reconocimiento', '2025-03-25',
                                                                             '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (66, 'FINISHED', '4. Rara vez siento que mi trabajo importa.', '2025-03-25',
                                                                             '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (67, 'FINISHED', '3. A veces puedo expresarme, pero con reservas', '2025-03-25',
                                                                             '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (68, 'FINISHED', '2. Generalmente hay oportunidades para relajarse', '2025-03-25',
                                                                             '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (69, 'FINISHED', '3. A veces siento apoyo, pero podría ser mejor', '2025-03-25',
                                                                             '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (70, 'FINISHED', '5. Nunca se fomenta la concentración', '2025-03-25',
                                                                             '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (71, 'FINISHED', '3. A veces se expresa, pero no mucho', '2025-03-25',
                                                                             '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (72, 'FINISHED', '4. Poco alineados', '2025-03-25',
                                                                             '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (6, 61), (6, 62), (6, 63), (6, 64), (6, 65), (6, 66), (6, 67), (6, 68), (6, 69), (6, 70), (6, 71), (6, 72);

/* TEAM2, módulo FINISHED:
   Survey id 7 para member4 */
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (73, 'FINISHED', '2. Bastante claro, aunque a veces tengo dudas', '2025-03-15',
                                                                             '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (74, 'FINISHED', '2. Casi siempre tengo autonomía suficiente', '2025-03-15',
                                                                             '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (75, 'FINISHED', '2. En general, hay apoyo suficiente', '2025-03-15',
                                                                             '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (76, 'FINISHED', '2. Generalmente, hay un buen nivel de conexión', '2025-03-15',
                                                                             '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (77, 'FINISHED', '2. Generalmente se reconocen, aunque no siempre.', '2025-03-15',
                                                                             '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (78, 'FINISHED', '2. Generalmente siento que estoy haciendo una diferencia', '2025-03-15',
                                                                             '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (79, 'FINISHED', '3. A veces puedo expresarme, pero con reservas', '2025-03-15',
                                                                             '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (80, 'FINISHED', '3. A veces hay momentos de diversión, pero no muchos', '2025-03-15',
                                                                             '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (81, 'FINISHED', '2. Generalmente me siento apoyado', '2025-03-15',
                                                                             '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (82, 'FINISHED', '3. A veces se fomenta, pero no siempre', '2025-03-15',
                                                                             '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (83, 'FINISHED', '4. Rara vez se promueve la atención plena', '2025-03-15',
                                                                             '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (84, 'FINISHED', '2. Bastante alineados', '2025-03-15',
                                                                             '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (7, 73), (7, 74), (7, 75), (7, 76), (7, 77), (7, 78), (7, 79), (7, 80), (7, 81), (7, 82), (7, 83), (7, 84);

/* TEAM2, módulo FINISHED:
   Survey id 8 para member5 */
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (85, 'FINISHED', '3. Algo confuso, no siempre lo entiendo', '2025-03-15',
                                                                             '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (86, 'FINISHED', '4. Rara vez puedo tomar decisiones por mi cuenta', '2025-03-15',
                                                                             '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (87, 'FINISHED', '3. A veces siento que me faltan oportunidades', '2025-03-15',
                                                                             '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (88, 'FINISHED', '1. Sí, siempre siento una conexión fuerte y apoyo mutuo', '2025-03-15',
                                                                             '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (89, 'FINISHED', '2. Generalmente se reconocen, aunque no siempre.', '2025-03-15',
                                                                             '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (90, 'FINISHED', '3. A veces siento que mi trabajo impacta, pero no siempre.', '2025-03-15',
                                                                             '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (91, 'FINISHED', '1. Siempre me siento seguro para expresarme', '2025-03-15',
                                                                             '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (92, 'FINISHED', '4. Rara vez hay espacio para divertirse', '2025-03-15',
                                                                             '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (93, 'FINISHED', '3. A veces siento apoyo, pero podría ser mejor', '2025-03-15',
                                                                             '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (94, 'FINISHED', '4. Rara vez se promueve la atención plena', '2025-03-15',
                                                                             '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (95, 'FINISHED', '2. Generalmente se expresa gratitud', '2025-03-15',
                                                                             '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (96, 'FINISHED', '3. Algo alineados', '2025-03-15',
                                                                             '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (8, 85), (8, 86), (8, 87), (8, 88), (8, 89), (8, 90), (8, 91), (8, 92), (8, 93), (8, 94), (8, 95), (8, 96);

/* TEAM2, módulo FINISHED:
   Survey id 9 para member6 */
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (97, 'FINISHED', '4. Poco claro, rara vez sé cómo contribuyo', '2025-03-15',
                                                                             '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (98, 'FINISHED', '5. Nunca tengo autonomía', '2025-03-15',
                                                                             '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (99, 'FINISHED', '1. Sí, siempre me siento apoyado para mejorar.', '2025-03-15',
                                                                             '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (100, 'FINISHED', '2. Generalmente, hay un buen nivel de conexión', '2025-03-15',
                                                                             '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (101, 'FINISHED', '3. A veces se reconocen, pero podría ser mejor', '2025-03-15',
                                                                             '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (102, 'FINISHED', '2. Generalmente siento que estoy haciendo una diferencia', '2025-03-15',
                                                                             '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (103, 'FINISHED', '2. Generalmente puedo hablar con confianza', '2025-03-15',
                                                                             '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (104, 'FINISHED', '3. A veces hay momentos de diversión, pero no muchos', '2025-03-15',
                                                                             '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (105, 'FINISHED', '2. Generalmente me siento apoyado', '2025-03-15',
                                                                             '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (106, 'FINISHED', '3. A veces se fomenta, pero no siempre', '2025-03-15',
                                                                             '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (107, 'FINISHED', '4. Rara vez se promueve la atención plena', '2025-03-15',
                                                                             '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (108, 'FINISHED', '3. Algo alineados', '2025-03-15',
                                                                             '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (9, 97), (9, 98), (9, 99), (9, 100), (9, 101), (9, 102), (9, 103), (9, 104), (9, 105), (9, 106), (9, 107), (9, 108);

/* TEAM3, módulo ACTIVE:
   Survey id 10 para member7 (FINISHED) */
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (109, 'FINISHED', '2. Bastante claro, aunque a veces tengo dudas', '2025-03-30',
                                                                             '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (110, 'FINISHED', '3. A veces puedo decidir, pero no siempre', '2025-03-30',
                                                                             '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (111, 'FINISHED', '2. En general, hay apoyo suficiente', '2025-03-30',
                                                                             '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (112, 'FINISHED', '1. Sí, siempre siento una conexión fuerte y apoyo mutuo', '2025-03-30',
                                                                             '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (113, 'FINISHED', '2. Generalmente se reconocen, aunque no siempre.', '2025-03-30',
                                                                             '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (114, 'FINISHED', '3. A veces siento que mi trabajo impacta, pero no siempre.', '2025-03-30',
                                                                             '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (115, 'FINISHED', '1. Siempre me siento seguro para expresarme', '2025-03-30',
                                                                             '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (116, 'FINISHED', '4. Rara vez hay espacio para divertirse', '2025-03-30',
                                                                             '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (117, 'FINISHED', '2. Generalmente me siento apoyado', '2025-03-30',
                                                                             '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (118, 'FINISHED', '3. A veces se fomenta, pero no siempre', '2025-03-30',
                                                                             '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (119, 'FINISHED', '4. Rara vez se promueve la atención plena', '2025-03-30',
                                                                             '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (120, 'FINISHED', '2. Bastante alineados', '2025-03-30',
                                                                             '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (10, 109), (10, 110), (10, 111), (10, 112), (10, 113), (10, 114), (10, 115), (10, 116), (10, 117), (10, 118), (10, 119), (10, 120);

/* TEAM3, módulo ACTIVE:
   Survey id 11 para member8 (ACTIVE) y Survey id 12 para member9 (ACTIVE)
   Se insertan actividades con estado ACTIVE (sin respuesta ni close_date)
*/

INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (121, 'ACTIVE', NULL, NULL, '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (122, 'ACTIVE', NULL, NULL, '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (123, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (124, 'ACTIVE', NULL, NULL, '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (125, 'ACTIVE', NULL, NULL, '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (126, 'ACTIVE', NULL, NULL, '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (127, 'ACTIVE', NULL, NULL, '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (128, 'ACTIVE', NULL, NULL, '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (129, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (130, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (131, 'ACTIVE', NULL, NULL, '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (132, 'ACTIVE', NULL, NULL, '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (11, 121), (11, 122), (11, 123), (11, 124), (11, 125), (11, 126), (11, 127), (11, 128), (11, 129), (11, 130), (11, 131), (11, 132);

INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (133, 'ACTIVE', NULL, NULL, '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (134, 'ACTIVE', NULL, NULL, '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (135, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (136, 'ACTIVE', NULL, NULL, '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (137, 'ACTIVE', NULL, NULL, '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (138, 'ACTIVE', NULL, NULL, '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (139, 'ACTIVE', NULL, NULL, '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (140, 'ACTIVE', NULL, NULL, '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (141, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (142, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (143, 'ACTIVE', NULL, NULL, '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (144, 'ACTIVE', NULL, NULL, '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (12, 133), (12, 134), (12, 135), (12, 136), (12, 137), (12, 138), (12, 139), (12, 140), (12, 141), (12, 142), (12, 143), (12, 144);


-- =============================================================
-- 1. INSERTAR 3 MÓDULOS ADICIONALES FINISHED PARA TEAM2
-- =============================================================
INSERT INTO module
(id, creation_date, date_and_time_to_close, date_and_time_to_publish, module_closed_date, module_state, name, team_uuid)
VALUES
    (5, '2025-04-01', '2025-04-06 18:00:00.000000', '2025-04-01 08:00:00.000000', '2025-04-06', 'FINISHED', 'TWELVE_STEPS',
     (SELECT uuid FROM team WHERE name = 'team2'));

INSERT INTO module
(id, creation_date, date_and_time_to_close, date_and_time_to_publish, module_closed_date, module_state, name, team_uuid)
VALUES
    (6, '2025-04-07', '2025-04-12 18:00:00.000000', '2025-04-07 08:00:00.000000', '2025-04-12', 'FINISHED', 'TWELVE_STEPS',
     (SELECT uuid FROM team WHERE name = 'team2'));

INSERT INTO module
(id, creation_date, date_and_time_to_close, date_and_time_to_publish, module_closed_date, module_state, name, team_uuid)
VALUES
    (7, '2025-04-13', '2025-04-18 18:00:00.000000', '2025-04-13 08:00:00.000000', '2025-04-18', 'FINISHED', 'TWELVE_STEPS',
     (SELECT uuid FROM team WHERE name = 'team2'));

-- =============================================================
-- 2. INSERTAR EN survey_module y twelve_steps_module para cada módulo
-- =============================================================
INSERT INTO survey_module (surveymodule_module) VALUES (5);
INSERT INTO twelve_steps_module (twelvesteps_module) VALUES (5);

INSERT INTO survey_module (surveymodule_module) VALUES (6);
INSERT INTO twelve_steps_module (twelvesteps_module) VALUES (6);

INSERT INTO survey_module (surveymodule_module) VALUES (7);
INSERT INTO twelve_steps_module (twelvesteps_module) VALUES (7);

-- =============================================================
-- 3. INSERTAR ENCUESTAS PARA TEAM2 EN LOS NUEVOS MÓDULOS
-- =============================================================
-- Para módulo con id = 5 (survey_module = 5)
INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (13, '2025-04-06', 'FINISHED', (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'), 5),
    (14, '2025-04-06', 'FINISHED', (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'), 5),
    (15, '2025-04-06', 'FINISHED', (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'), 5);

-- Para módulo con id = 6 (survey_module = 6)
INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (16, '2025-04-12', 'FINISHED', (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'), 6),
    (17, '2025-04-12', 'FINISHED', (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'), 6),
    (18, '2025-04-12', 'FINISHED', (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'), 6);

-- Para módulo con id = 7 (survey_module = 7)
INSERT INTO survey (id, close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (19, '2025-04-18', 'FINISHED', (SELECT uuid FROM user_model WHERE username = 'member4@gmail.com'), 7),
    (20, '2025-04-18', 'FINISHED', (SELECT uuid FROM user_model WHERE username = 'member5@gmail.com'), 7),
    (21, '2025-04-18', 'FINISHED', (SELECT uuid FROM user_model WHERE username = 'member6@gmail.com'), 7);

-- =============================================================
-- 4. INSERTAR ACTIVIDADES Y VINCULACIÓN EN survey_activities
-- Cada encuesta tendrá 12 actividades.

-- ---------------------------
-- MÓDULO id = 5, Survey id 13 (member4)
-- Asignamos activity IDs 145 a 156
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (145, 'FINISHED', '1. Totalmente claro y entiendo mi contribución', '2025-04-06', '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (146, 'FINISHED', '2. Casi siempre tengo autonomía suficiente', '2025-04-06', '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (147, 'FINISHED', '3. A veces siento que me faltan oportunidades', '2025-04-06', '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (148, 'FINISHED', '2. Generalmente, hay un buen nivel de conexión', '2025-04-06', '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (149, 'FINISHED', '3. A veces se reconocen, pero podría ser mejor', '2025-04-06', '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (150, 'FINISHED', '2. Generalmente siento que estoy haciendo una diferencia', '2025-04-06', '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (151, 'FINISHED', '1. Siempre me siento seguro para expresarme', '2025-04-06', '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (152, 'FINISHED', '3. A veces hay momentos de diversión, pero no muchos', '2025-04-06', '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (153, 'FINISHED', '2. Generalmente me siento apoyado', '2025-04-06', '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (154, 'FINISHED', '3. A veces se fomenta, pero no siempre', '2025-04-06', '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (155, 'FINISHED', '4. Rara vez siento gratitud en el equipo', '2025-04-06', '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (156, 'FINISHED', '2. Bastante alineados', '2025-04-06', '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (13, 145), (13, 146), (13, 147), (13, 148), (13, 149), (13, 150), (13, 151), (13, 152), (13, 153), (13, 154), (13, 155), (13, 156);

-- ---------------------------
-- MÓDULO id = 5, Survey id 14 (member5)
-- Asignamos activity IDs 157 a 168
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (157, 'FINISHED', '2. Bastante claro, aunque a veces tengo dudas', '2025-04-06', '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (158, 'FINISHED', '3. A veces puedo decidir, pero no siempre', '2025-04-06', '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (159, 'FINISHED', '1. Sí, siempre me siento apoyado para mejorar.', '2025-04-06', '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (160, 'FINISHED', '3. A veces hay conexión, pero no siempre', '2025-04-06', '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (161, 'FINISHED', '1. Siempre se reconocen y valoran', '2025-04-06', '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (162, 'FINISHED', '3. A veces siento que mi trabajo impacta, pero no siempre.', '2025-04-06', '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (163, 'FINISHED', '2. Generalmente puedo hablar con confianza', '2025-04-06', '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (164, 'FINISHED', '1. Siempre hay momentos divertidos y relajantes', '2025-04-06', '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (165, 'FINISHED', '3. A veces siento apoyo, pero podría ser mejor', '2025-04-06', '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (166, 'FINISHED', '2. Generalmente se fomenta la atención plena', '2025-04-06', '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (167, 'FINISHED', '5. Nunca se practica la gratitud', '2025-04-06', '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (168, 'FINISHED', '3. Algo alineados', '2025-04-06', '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (14, 157), (14, 158), (14, 159), (14, 160), (14, 161), (14, 162), (14, 163), (14, 164), (14, 165), (14, 166), (14, 167), (14, 168);

-- ---------------------------
-- MÓDULO id = 5, Survey id 15 (member6)
-- Asignamos activity IDs 169 a 180
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (169, 'FINISHED', '3. Algo confuso, no siempre lo entiendo', '2025-04-06', '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (170, 'FINISHED', '4. Poco claro, rara vez sé cómo contribuyo', '2025-04-06', '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (171, 'FINISHED', '5. Nunca siento que hay interés en mi crecimiento', '2025-04-06', '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (172, 'FINISHED', '1. Sí, siempre siento una conexión fuerte y apoyo mutuo', '2025-04-06', '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (173, 'FINISHED', '5. Nunca me siento reconocido.', '2025-04-06', '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (174, 'FINISHED', '4. Rara vez siento que mi trabajo importa.', '2025-04-06', '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (175, 'FINISHED', '5. Nunca me siento seguro para compartir mis pensamientos', '2025-04-06', '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (176, 'FINISHED', '5. Nunca hay espacio para diversión o relajación', '2025-04-06', '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (177, 'FINISHED', '5. Nunca siento que el equipo se preocupe por mi desarrollo', '2025-04-06', '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (178, 'FINISHED', '5. Nunca se fomenta la concentración', '2025-04-06', '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (179, 'FINISHED', '2. Generalmente se expresa gratitud', '2025-04-06', '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (180, 'FINISHED', '5. Completamente desalineados', '2025-04-06', '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (15, 169), (15, 170), (15, 171), (15, 172), (15, 173), (15, 174), (15, 175), (15, 176), (15, 177), (15, 178), (15, 179), (15, 180);

-- ---------------------------
-- MÓDULO id = 6, para TEAM2
-- Survey id 16 para member4 (close_date = '2025-04-12')
-- Asignamos activity IDs 181 a 192
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (181, 'FINISHED', '1. Totalmente claro y entiendo mi contribución', '2025-04-12', '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (182, 'FINISHED', '2. Casi siempre tengo autonomía suficiente', '2025-04-12', '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (183, 'FINISHED', '2. En general, hay apoyo suficiente', '2025-04-12', '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (184, 'FINISHED', '2. Generalmente, hay un buen nivel de conexión', '2025-04-12', '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (185, 'FINISHED', '1. Siempre se reconocen y valoran', '2025-04-12', '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (186, 'FINISHED', '1. Siempre siento que mi trabajo tiene impacto.', '2025-04-12', '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (187, 'FINISHED', '1. Siempre me siento seguro para expresarme', '2025-04-12', '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (188, 'FINISHED', '1. Siempre hay momentos divertidos y relajantes', '2025-04-12', '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (189, 'FINISHED', '1. Siempre me siento apoyado en mi desarrollo', '2025-04-12', '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (190, 'FINISHED', '1. Siempre se fomenta la atención plena', '2025-04-12', '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (191, 'FINISHED', '1. Siempre se practica y se siente genuina', '2025-04-12', '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (192, 'FINISHED', '1. Totalmente alineados', '2025-04-12', '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (16, 181), (16, 182), (16, 183), (16, 184), (16, 185), (16, 186), (16, 187), (16, 188), (16, 189), (16, 190), (16, 191), (16, 192);

-- Survey id 17 para member5 (módulo id = 6), asignar activity IDs 193 a 204
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (193, 'FINISHED', '2. Bastante claro, aunque a veces tengo dudas', '2025-04-12', '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (194, 'FINISHED', '3. A veces puedo decidir, pero no siempre', '2025-04-12', '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (195, 'FINISHED', '3. En general, hay apoyo suficiente', '2025-04-12', '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (196, 'FINISHED', '3. A veces hay conexión, pero no siempre', '2025-04-12', '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (197, 'FINISHED', '2. Generalmente se reconocen, aunque no siempre.', '2025-04-12', '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (198, 'FINISHED', '2. Generalmente siento que estoy haciendo una diferencia', '2025-04-12', '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (199, 'FINISHED', '2. Generalmente puedo hablar con confianza', '2025-04-12', '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (200, 'FINISHED', '2. Generalmente hay oportunidades para relajarse', '2025-04-12', '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (201, 'FINISHED', '2. Generalmente me siento apoyado', '2025-04-12', '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (202, 'FINISHED', '2. Generalmente se fomenta la atención plena', '2025-04-12', '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (203, 'FINISHED', '2. Generalmente se expresa gratitud', '2025-04-12', '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (204, 'FINISHED', '2. Bastante alineados', '2025-04-12', '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (17, 193), (17, 194), (17, 195), (17, 196), (17, 197), (17, 198), (17, 199), (17, 200), (17, 201), (17, 202), (17, 203), (17, 204);

-- Survey id 18 para member6 (módulo id = 6), asignar activity IDs 205 a 216
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (205, 'FINISHED', '3. Algo confuso, no siempre lo entiendo', '2025-04-12', '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (206, 'FINISHED', '4. Poco claro, rara vez sé cómo contribuyo', '2025-04-12', '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (207, 'FINISHED', '4. Rara vez recibo apoyo para mejorar.', '2025-04-12', '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (208, 'FINISHED', '4. Rara vez siento una conexión real en el equipo', '2025-04-12', '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (209, 'FINISHED', '3. A veces se reconocen, pero podría ser mejor', '2025-04-12', '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (210, 'FINISHED', '3. A veces siento que mi trabajo impacta, pero no siempre.', '2025-04-12', '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (211, 'FINISHED', '3. A veces puedo expresarme, pero con reservas', '2025-04-12', '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (212, 'FINISHED', '3. A veces hay momentos de diversión, pero no muchos', '2025-04-12', '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (213, 'FINISHED', '3. A veces siento apoyo, pero podría ser mejor', '2025-04-12', '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (214, 'FINISHED', '3. A veces se fomenta, pero no siempre', '2025-04-12', '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (215, 'FINISHED', '3. A veces se expresa, pero no mucho', '2025-04-12', '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (216, 'FINISHED', '3. Algo alineados', '2025-04-12', '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (18, 205), (18, 206), (18, 207), (18, 208), (18, 209), (18, 210), (18, 211), (18, 212), (18, 213), (18, 214), (18, 215), (18, 216);

-- ---------------------------
-- MÓDULO id = 7, para TEAM2
-- Survey id 19 para member4, asignar activity IDs 217 a 228
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (217, 'FINISHED', '1. Totalmente claro y entiendo mi contribución', '2025-04-18', '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (218, 'FINISHED', '1. Siempre puedo tomar decisiones libremente', '2025-04-18', '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (219, 'FINISHED', '1. Sí, siempre me siento apoyado para mejorar.', '2025-04-18', '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (220, 'FINISHED', '1. Sí, siempre siento una conexión fuerte y apoyo mutuo', '2025-04-18', '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (221, 'FINISHED', '1. Siempre se reconocen y valoran', '2025-04-18', '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (222, 'FINISHED', '1. Siempre siento que mi trabajo tiene impacto.', '2025-04-18', '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (223, 'FINISHED', '1. Siempre me siento seguro para expresarme', '2025-04-18', '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (224, 'FINISHED', '1. Siempre hay momentos divertidos y relajantes', '2025-04-18', '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (225, 'FINISHED', '1. Siempre me siento apoyado en mi desarrollo', '2025-04-18', '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (226, 'FINISHED', '1. Siempre se fomenta la atención plena', '2025-04-18', '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (227, 'FINISHED', '1. Siempre se practica y se siente genuina', '2025-04-18', '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (228, 'FINISHED', '1. Totalmente alineados', '2025-04-18', '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (19, 217), (19, 218), (19, 219), (19, 220), (19, 221), (19, 222), (19, 223), (19, 224), (19, 225), (19, 226), (19, 227), (19, 228);

-- Survey id 20 para member5 (módulo id = 7), asignar activity IDs 229 a 240
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (229, 'FINISHED', '2. Bastante claro, aunque a veces tengo dudas', '2025-04-18', '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (230, 'FINISHED', '2. Casi siempre tengo autonomía suficiente', '2025-04-18', '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (231, 'FINISHED', '2. En general, hay apoyo suficiente', '2025-04-18', '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (232, 'FINISHED', '2. Generalmente, hay un buen nivel de conexión', '2025-04-18', '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (233, 'FINISHED', '2. Generalmente se reconocen, aunque no siempre.', '2025-04-18', '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (234, 'FINISHED', '2. Generalmente siento que estoy haciendo una diferencia', '2025-04-18', '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (235, 'FINISHED', '2. Generalmente puedo hablar con confianza', '2025-04-18', '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (236, 'FINISHED', '2. Generalmente hay oportunidades para relajarse', '2025-04-18', '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (237, 'FINISHED', '2. Generalmente me siento apoyado', '2025-04-18', '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (238, 'FINISHED', '2. Generalmente se fomenta la atención plena', '2025-04-18', '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (239, 'FINISHED', '2. Generalmente se expresa gratitud', '2025-04-18', '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (240, 'FINISHED', '2. Bastante alineados', '2025-04-18', '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (20, 229), (20, 230), (20, 231), (20, 232), (20, 233), (20, 234), (20, 235), (20, 236), (20, 237), (20, 238), (20, 239), (20, 240);

-- Survey id 21 para member6 (módulo id = 7), asignar activity IDs 241 a 252
INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (241, 'FINISHED', '3. Algo confuso, no siempre lo entiendo', '2025-04-18', '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (242, 'FINISHED', '5. Completamente confuso, no entiendo el propósito del equipo', '2025-04-18', '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (243, 'FINISHED', '3. A veces siento que me faltan oportunidades', '2025-04-18', '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (244, 'FINISHED', '4. Rara vez siento una conexión real en el equipo', '2025-04-18', '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (245, 'FINISHED', '3. A veces se reconocen, pero podría ser mejor', '2025-04-18', '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (246, 'FINISHED', '3. A veces siento que mi trabajo impacta, pero no siempre.', '2025-04-18', '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (247, 'FINISHED', '3. A veces puedo expresarme, pero con reservas', '2025-04-18', '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (248, 'FINISHED', '4. Rara vez hay espacio para divertirse', '2025-04-18', '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (249, 'FINISHED', '3. A veces siento apoyo, pero podría ser mejor', '2025-04-18', '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (250, 'FINISHED', '4. Rara vez se promueve la atención plena', '2025-04-18', '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (251, 'FINISHED', '4. Rara vez siento gratitud en el equipo', '2025-04-18', '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (252, 'FINISHED', '5. Completamente desalineados', '2025-04-18', '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (21, 241), (21, 242), (21, 243), (21, 244), (21, 245), (21, 246), (21, 247), (21, 248), (21, 249), (21, 250), (21, 251), (21, 252);


INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (253, 'ACTIVE', NULL, NULL, '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (254, 'ACTIVE', NULL, NULL, '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (255, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (256, 'ACTIVE', NULL, NULL, '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (257, 'ACTIVE', NULL, NULL, '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (258, 'ACTIVE', NULL, NULL, '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (259, 'ACTIVE', NULL, NULL, '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (260, 'ACTIVE', NULL, NULL, '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (261, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (262, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (263, 'ACTIVE', NULL, NULL, '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (264, 'ACTIVE', NULL, NULL, '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (2, 253), (2, 254), (2, 255), (2, 256), (2, 257), (2, 258), (2, 259), (2, 260), (2, 261), (2, 262), (2, 263), (2, 264);

INSERT INTO activity (id, activity_state, answer, close_date, question) VALUES
                                                                            (265, 'ACTIVE', NULL, NULL, '¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?'),
                                                                            (266, 'ACTIVE', NULL, NULL, '¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?'),
                                                                            (267, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?'),
                                                                            (268, 'ACTIVE', NULL, NULL, '¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?'),
                                                                            (269, 'ACTIVE', NULL, NULL, '¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?'),
                                                                            (270, 'ACTIVE', NULL, NULL, '¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?'),
                                                                            (271, 'ACTIVE', NULL, NULL, '¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?'),
                                                                            (272, 'ACTIVE', NULL, NULL, '¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?'),
                                                                            (273, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?'),
                                                                            (274, 'ACTIVE', NULL, NULL, '¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?'),
                                                                            (275, 'ACTIVE', NULL, NULL, '¿Sientes que la gratitud se expresa regularmente en el equipo?'),
                                                                            (276, 'ACTIVE', NULL, NULL, '¿Sientes que tus valores y objetivos personales están alineados con los del equipo?');

INSERT INTO survey_activities (survey_id, activities_id)
VALUES
    (3, 265), (3, 266), (3, 267), (3, 268), (3, 269), (3, 270), (3, 271), (3, 272), (3, 273), (3, 274), (3, 275), (3, 276);
