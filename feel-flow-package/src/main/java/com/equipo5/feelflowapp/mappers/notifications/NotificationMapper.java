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

    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "notificationTypeEnum", target = "notificationType")
    @Mapping(source = "createdAt", target = "creationDate")
    NotificationDto notificationToNotificationDto(Notification notification);

    Notification notificationClientDtoToNotification(NotificationClientDto notificationClientDto);

}
