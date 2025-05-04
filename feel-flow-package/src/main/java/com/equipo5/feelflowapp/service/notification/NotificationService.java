package com.equipo5.feelflowapp.service.notification;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.notifications.NotificationClientDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationSessionUserDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    void sendNotification(NotificationClientDto notificationDto);
    void sendNotificationModule(List<RegularUser> users, String body, String title);
    void sendNotificationModule(TeamLeader teamLeader, String body, String title);
    String generateBodyForOpenedModule(String nameModule, Timestamp dateAndTimeToPublish, Timestamp dateAndTimeToClose);

    String generateBodyForCloseModule(String nameModule);

    List<NotificationSessionUserDto> getNotificationsAvailableToSend(Integer max);
    List<NotificationDto> getNotificationsByUser(NotificationTypeEnum notificationType);

    void sendNotificationSurvey(Survey survey);
    void sendNotificationSurveyAvailableNikoNiko(Module module);
}
