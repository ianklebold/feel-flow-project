package com.equipo5.feelflowapp.service.notification.impl;

import com.equipo5.feelflowapp.domain.notifications.Notification;
import com.equipo5.feelflowapp.dto.notifications.NotificationClientDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationDto;
import com.equipo5.feelflowapp.mappers.notifications.NotificationMapper;
import com.equipo5.feelflowapp.repository.notifications.NotificationRepository;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    private final NotificationMapper notificationMapper;

    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotification(NotificationClientDto notificationDto) {
        //Guardar en base de datos la notificacion
        Notification notificationEntity = notificationMapper.notificationClientDtoToNotification( notificationDto );
        notificationEntity.setWasRead( Boolean.FALSE );
        notificationEntity.setWasSeen( Boolean.FALSE );
        notificationEntity.setCreatedAt( LocalDateTime.now() );
        this.notificationRepository.save(notificationEntity);


        NotificationDto notification = notificationMapper.notificationClientDtoToNotificationDto( notificationDto );
        messagingTemplate.convertAndSend("/topic/" + notificationDto.uuidTeam().toString(), notification );
    }
}
