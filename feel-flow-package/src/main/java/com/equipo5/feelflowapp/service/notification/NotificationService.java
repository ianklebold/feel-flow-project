package com.equipo5.feelflowapp.service.notification;

import com.equipo5.feelflowapp.dto.notifications.NotificationClientDto;

public interface NotificationService {
    void sendNotification(NotificationClientDto notificationDto);
}
