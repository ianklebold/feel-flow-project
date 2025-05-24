-- =============================================================
-- 0. Variables de entorno (continuamos IDs tras el script anterior)
-- =============================================================
SET @team4        = _utf8mb4'UTN DEVS' COLLATE utf8mb4_unicode_ci;
SET @mod5          = 5;
SET @mod6          = 6;
SET @mod7          = 7;
SET @mod8          = 8;
SET @mod9          = 9;

-- =============================================================
-- A. INSERCIÓN DE 5 MÓDULOS NIKO_NIKO (diciembre ’24 – abril ’25) para UTN DEVS
-- Todos FINISHED
-- =============================================================
INSERT INTO module
(id, creation_date, date_and_time_to_publish, date_and_time_to_close, module_closed_date, module_state, name, team_uuid)
VALUES
    (@mod5, '2024-12-01', '2024-12-01 08:00:00', '2024-12-31 18:00:00', '2024-12-31', 'FINISHED', 'NIKO_NIKO',
     (SELECT uuid FROM team WHERE name = @team4)),
    (@mod6, '2025-01-01', '2025-01-01 08:00:00', '2025-01-31 18:00:00', '2025-01-31', 'FINISHED', 'NIKO_NIKO',
     (SELECT uuid FROM team WHERE name = @team4)),
    (@mod7, '2025-02-01', '2025-02-01 08:00:00', '2025-02-28 18:00:00', '2025-02-28', 'FINISHED', 'NIKO_NIKO',
     (SELECT uuid FROM team WHERE name = @team4)),
    (@mod8, '2025-03-01', '2025-03-01 08:00:00', '2025-03-31 18:00:00', '2025-03-31', 'FINISHED', 'NIKO_NIKO',
     (SELECT uuid FROM team WHERE name = @team4)),
    (@mod9, '2025-04-01', '2025-04-01 08:00:00', '2025-04-30 18:00:00', '2025-04-30', 'FINISHED', 'NIKO_NIKO',
     (SELECT uuid FROM team WHERE name = @team4));

-- =============================================================
-- B. REGISTRO EN survey_module y niko_niko_module
-- =============================================================
INSERT INTO survey_module (surveymodule_module) VALUES
                                                    (@mod5), (@mod6), (@mod7), (@mod8), (@mod9);

INSERT INTO niko_niko_module (niko_niko_module, time_to_to_response_start_day, time_to_to_response_end_day) VALUES
                                                                                                                (@mod5, '09:00:00', '12:00:00'),
                                                                                                                (@mod6, '09:00:00', '12:00:00'),
                                                                                                                (@mod7, '09:00:00', '12:00:00'),
                                                                                                                (@mod8, '09:00:00', '12:00:00'),
                                                                                                                (@mod9, '09:00:00', '12:00:00');

-- =============================================================
-- C. PROCEDIMIENTO PARA INSERTAR ENCUESTAS “CLOSED” Y ACTIVIDADES “FINISHED”
--    + registro en activity_niko_niko
-- =============================================================
DELIMITER //
CREATE PROCEDURE insert_nikoniko_for_team4(
    IN p_module_id   BIGINT,
    IN p_start_date  DATE,
    IN p_end_date    DATE,
    IN p_team_name   VARCHAR(100)
)
BEGIN
  DECLARE l_current    DATE;
  DECLARE l_user_uuid  CHAR(36);
  DECLARE done         INT DEFAULT 0;
  DECLARE v_survey_id  BIGINT;
  DECLARE v_act_id     BIGINT;

  -- Cursor sobre los miembros del equipo
  DECLARE cur_users CURSOR FOR
SELECT regularuser_persona
FROM regular_user
WHERE team_uuid = (SELECT uuid FROM team WHERE name = p_team_name);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

  SET l_current = p_start_date;

  WHILE l_current <= p_end_date DO
    SET done = 0;
OPEN cur_users;
user_loop: LOOP
      FETCH cur_users INTO l_user_uuid;
      IF done = 1 THEN
        LEAVE user_loop;
END IF;

      -- 1) Inserto encuesta cerrada
INSERT INTO survey
(close_date, survey_state_enum, regular_user_regularuser_persona, survey_module_surveymodule_module)
VALUES
    (l_current, 'CLOSED', l_user_uuid, p_module_id);
SET v_survey_id = LAST_INSERT_ID();

      -- 2) Actividad de la mañana
INSERT INTO activity
(activity_state, answer, close_date, question)
VALUES
    ('FINISHED',
     CASE MOD(DAY(l_current),3)
         WHEN 0 THEN 'Normal'
         WHEN 1 THEN 'Mal'
         ELSE 'Muy Mal'
         END,
     l_current,
     '¿Cómo te sientes el día de hoy?');
SET v_act_id = LAST_INSERT_ID();
INSERT INTO survey_activities (survey_id, activities_id)
VALUES (v_survey_id, v_act_id);
-- registro en activity_niko_niko
INSERT INTO activity_niko_niko
(nikonikoactivity_activity, description_feeling, day_of_week)
VALUES
    (
        v_act_id,
        CONCAT(
                'Inicio: ',
                l_user_uuid,
                ' se siente ',
                CASE MOD(DAY(l_current),3)
                    WHEN 0 THEN 'Normal'
                    WHEN 1 THEN 'Mal'
                    ELSE 'Muy Mal'
                    END
        ),
        DAYOFWEEK(l_current) - 1
    );

-- 3) Actividad de la tarde
INSERT INTO activity
(activity_state, answer, close_date, question)
VALUES
    ('FINISHED',
     CASE MOD(DAY(l_current)+1,3)
         WHEN 0 THEN 'Normal'
         WHEN 1 THEN 'Mal'
         ELSE 'Muy Mal'
         END,
     l_current,
     '¿Cómo te sientes luego de la jornada laboral?');
SET v_act_id = LAST_INSERT_ID();
INSERT INTO survey_activities (survey_id, activities_id)
VALUES (v_survey_id, v_act_id);
-- registro en activity_niko_niko
INSERT INTO activity_niko_niko
(nikonikoactivity_activity, description_feeling, day_of_week)
VALUES
    (
        v_act_id,
        CONCAT(
                'Fin: ',
                l_user_uuid,
                ' se siente ',
                CASE MOD(DAY(l_current)+1,3)
                    WHEN 0 THEN 'Normal'
                    WHEN 1 THEN 'Mal'
                    ELSE 'Muy Mal'
                    END
        ),
        DAYOFWEEK(l_current) - 1
    );

END LOOP user_loop;
CLOSE cur_users;
SET l_current = DATE_ADD(l_current, INTERVAL 1 DAY);
END WHILE;
END;
//
DELIMITER ;

-- =============================================================
-- D. LLAMADAS AL PROCEDIMIENTO PARA CADA UNO DE LOS 5 MÓDULOS
-- =============================================================
CALL insert_nikoniko_for_team4(@mod5, '2024-12-01', '2024-12-31', @team4);
CALL insert_nikoniko_for_team4(@mod6, '2025-01-01', '2025-01-31', @team4);
CALL insert_nikoniko_for_team4(@mod7, '2025-02-01', '2025-02-28', @team4);
CALL insert_nikoniko_for_team4(@mod8, '2025-03-01', '2025-03-31', @team4);
CALL insert_nikoniko_for_team4(@mod9, '2025-04-01', '2025-04-30', @team4);