package com.equipo5.feelflowapp.mappers.notifications;

import com.equipo5.feelflowapp.domain.notifications.Notification;
import com.equipo5.feelflowapp.dto.notifications.NotificationClientDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NotificationMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    NotificationDto notificationClientDtoToNotificationDto(NotificationClientDto notification);

    Notification notificationClientDtoToNotification(NotificationClientDto notificationClientDto);

}
