package com.equipo5.feelflowapp.service.notification;

import com.equipo5.feelflowapp.dto.notifications.NotificationClientDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationSessionUserDto;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    void sendNotification(NotificationClientDto notificationDto);

    List<NotificationSessionUserDto> getBadgesAvailableToSend(LocalDateTime from, LocalDateTime to, Integer max);
}
