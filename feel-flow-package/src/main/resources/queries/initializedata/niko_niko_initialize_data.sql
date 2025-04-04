-- =============================================================
-- A. INSERCIÓN DE MÓDULOS NIKO NIKO
-- =============================================================

-- Módulo ACTIVE para team1 (ID = 10, período: 2025-05-01 a 2025-05-31)
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (12, '2025-05-01', '2025-05-01 08:00:00.000000', '2025-05-31 18:00:00.000000', NULL, 'ACTIVE', 'NIKO_NIKO',
     (SELECT uuid FROM team WHERE name = 'team1'));

-- Módulos FINISHED para team2
-- Módulo 1 para team2 (ID = 11, período: 2025-02-01 a 2025-02-28)
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (13, '2025-02-01', '2025-02-01 08:00:00.000000', '2025-02-28 18:00:00.000000', '2025-02-28', 'FINISHED', 'NIKO_NIKO',
     (SELECT uuid FROM team WHERE name = 'team2'));

-- Módulo 2 para team2 (ID = 12, período: 2025-03-01 a 2025-03-31)
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (14, '2025-03-01', '2025-03-01 08:00:00.000000', '2025-03-31 18:00:00.000000', '2025-03-31', 'FINISHED', 'NIKO_NIKO',
     (SELECT uuid FROM team WHERE name = 'team2'));

-- Módulo 3 para team2 (ID = 13, período: 2025-04-01 a 2025-04-30)
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (15, '2025-04-01', '2025-04-01 08:00:00.000000', '2025-04-30 18:00:00.000000', '2025-04-30', 'FINISHED', 'NIKO_NIKO',
     (SELECT uuid FROM team WHERE name = 'team2'));

-- Módulo ACTIVE para team3 (ID = 14, período: 2025-06-01 a 2025-06-30)
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (16, '2025-06-01', '2025-06-01 08:00:00.000000', '2025-06-30 18:00:00.000000', NULL, 'ACTIVE', 'NIKO_NIKO',
     (SELECT uuid FROM team WHERE name = 'team3'));

-- =============================================================
-- B. REGISTRO EN survey_module y niko_niko_module
-- =============================================================
-- Para módulo ID 10 (Team1)
INSERT INTO survey_module (surveymodule_module) VALUES (12);
INSERT INTO niko_niko_module (niko_niko_module, time_to_to_response_start_day, time_to_to_response_end_day)
VALUES (12, '09:00:00', '12:00:00');

-- Para módulo ID 11 (Team2)
INSERT INTO survey_module (surveymodule_module) VALUES (13);
INSERT INTO niko_niko_module (niko_niko_module, time_to_to_response_start_day, time_to_to_response_end_day)
VALUES (13, '09:00:00', '12:00:00');

-- Para módulo ID 12 (Team2)
INSERT INTO survey_module (surveymodule_module) VALUES (14);
INSERT INTO niko_niko_module (niko_niko_module, time_to_to_response_start_day, time_to_to_response_end_day)
VALUES (14, '09:00:00', '12:00:00');

-- Para módulo ID 13 (Team2)
INSERT INTO survey_module (surveymodule_module) VALUES (15);
INSERT INTO niko_niko_module (niko_niko_module, time_to_to_response_start_day, time_to_to_response_end_day)
VALUES (15, '09:00:00', '12:00:00');

-- Para módulo ID 14 (Team3)
INSERT INTO survey_module (surveymodule_module) VALUES (16);
INSERT INTO niko_niko_module (niko_niko_module, time_to_to_response_start_day, time_to_to_response_end_day)
VALUES (16, '09:00:00', '12:00:00');

-- =============================================================
-- C. PROCEDIMIENTO ALMACENADO PARA INSERTAR ENCUESTAS Y ACTIVIDADES
-- =============================================================
DELIMITER $$
CREATE PROCEDURE insert_nikoniko_surveys(
    IN p_module_id BIGINT,
    IN p_start_date DATE,
    IN p_end_date DATE,
    IN p_finished_cutoff DATE,  -- Las encuestas para días antes de este serán FINISHED; de lo contrario, ACTIVE.
    IN p_team_name VARCHAR(50)
)
BEGIN
  DECLARE l_current DATE;
  DECLARE l_reg_user VARCHAR(36);
  DECLARE l_state VARCHAR(20);
  DECLARE l_cd DATE;
  DECLARE done INT DEFAULT 0;

  DECLARE cur_user CURSOR FOR
SELECT regularuser_persona
FROM regular_user
WHERE team_uuid = (SELECT uuid FROM team WHERE name = p_team_name);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

  SET l_current = p_start_date;

  WHILE l_current <= p_end_date DO
    SET done = 0;
OPEN cur_user;
read_loop: LOOP
       FETCH cur_user INTO l_reg_user;
       IF done = 1 THEN
         LEAVE read_loop;
END IF;

       IF l_current < p_finished_cutoff THEN
         SET l_state = 'FINISHED';
         SET l_cd = l_current;
ELSE
         SET l_state = 'ACTIVE';
         SET l_cd = NULL;
END IF;

INSERT INTO survey (close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES (l_cd, l_state, l_reg_user, (SELECT surveymodule_module FROM survey_module WHERE surveymodule_module = p_module_id LIMIT 1));
SET @survey_id = LAST_INSERT_ID();

       IF l_state = 'FINISHED' THEN
         INSERT INTO activity (activity_state, answer, close_date, question)
         VALUES ('FINISHED',
           CASE MOD(DAY(l_current),5)
             WHEN 0 THEN 'Muy Bien'
             WHEN 1 THEN 'Bien'
             WHEN 2 THEN 'Normal'
             WHEN 3 THEN 'Mal'
             ELSE 'Muy Mal'
           END,
           l_current,
           '¿Cómo te sientes el día de hoy?');
ELSE
         INSERT INTO activity (activity_state, answer, close_date, question)
         VALUES ('ACTIVE', NULL, NULL, '¿Cómo te sientes el día de hoy?');
END IF;
       SET @act1 = LAST_INSERT_ID();
INSERT INTO survey_activities (survey_id, activities_id) VALUES (@survey_id, @act1);
IF l_state = 'FINISHED' THEN
         INSERT INTO activity_niko_niko (nikonikoactivity_activity, description_feeling, day_of_week)
         VALUES (@act1, CONCAT('Inicio: ', l_reg_user, ' se siente ',
                     CASE MOD(DAY(l_current),5)
                       WHEN 0 THEN 'excelente'
                       WHEN 1 THEN 'bien'
                       WHEN 2 THEN 'regular'
                       WHEN 3 THEN 'mal'
                       ELSE 'muy mal'
                     END), DAYOFWEEK(l_current)-1);
END IF;

       IF l_state = 'FINISHED' THEN
         INSERT INTO activity (activity_state, answer, close_date, question)
         VALUES ('FINISHED',
           CASE MOD(DAY(l_current)+1,5)
             WHEN 0 THEN 'Muy Bien'
             WHEN 1 THEN 'Bien'
             WHEN 2 THEN 'Normal'
             WHEN 3 THEN 'Mal'
             ELSE 'Muy Mal'
           END,
           l_current,
           '¿Cómo te sientes luego de la jornada laboral?');
ELSE
         INSERT INTO activity (activity_state, answer, close_date, question)
         VALUES ('ACTIVE', NULL, NULL, '¿Cómo te sientes luego de la jornada laboral?');
END IF;
       SET @act2 = LAST_INSERT_ID();
INSERT INTO survey_activities (survey_id, activities_id) VALUES (@survey_id, @act2);
IF l_state = 'FINISHED' THEN
         INSERT INTO activity_niko_niko (nikonikoactivity_activity, description_feeling, day_of_week)
         VALUES (@act2, CONCAT('Fin: ', l_reg_user, ' se siente ',
                     CASE MOD(DAY(l_current)+1,5)
                       WHEN 0 THEN 'excelente'
                       WHEN 1 THEN 'bien'
                       WHEN 2 THEN 'regular'
                       WHEN 3 THEN 'mal'
                       ELSE 'muy mal'
                     END), DAYOFWEEK(l_current)-1);
END IF;
END LOOP;
CLOSE cur_user;
SET l_current = DATE_ADD(l_current, INTERVAL 1 DAY);
END WHILE;
END$$
DELIMITER ;

-- =============================================================
-- D. LLAMADAS AL PROCEDIMIENTO PARA CADA MÓDULO
-- =============================================================

-- TEAM1: Módulo ACTIVE (ID 12), período 2025-05-01 a 2025-05-31.
-- Para este ejemplo, definimos que las encuestas hasta el 15 de mayo se marcarán FINISHED.
CALL insert_nikoniko_surveys(12, '2025-05-01', '2025-05-31', '2025-05-16', 'team1');

-- TEAM2: Tres módulos FINISHED.
-- Módulo ID 13, período 2025-02-01 a 2025-02-28 (todas FINISHED)
CALL insert_nikoniko_surveys(13, '2025-02-01', '2025-02-28', '2025-02-28', 'team2');

-- Módulo ID 14, período 2025-03-01 a 2025-03-31 (todas FINISHED)
CALL insert_nikoniko_surveys(14, '2025-03-01', '2025-03-31', '2025-03-31', 'team2');

-- Módulo ID 15, período 2025-04-01 a 2025-04-30 (todas FINISHED)
CALL insert_nikoniko_surveys(15, '2025-04-01', '2025-04-30', '2025-04-30', 'team2');

-- TEAM3: Módulo ACTIVE (ID 16), período 2025-06-01 a 2025-06-30.
-- En este caso, encuestas hasta el 16 de junio se marcarán FINISHED, luego ACTIVE.
CALL insert_nikoniko_surveys(16, '2025-06-01', '2025-06-30', '2025-06-16', 'team3');