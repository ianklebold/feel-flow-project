package com.equipo5.feelflowapp.mappers.notifications;

import com.equipo5.feelflowapp.domain.notifications.Notification;
import com.equipo5.feelflowapp.dto.notifications.NotificationSessionUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NotificationSessionUserMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "createdAt", target = "createdAt")
    NotificationSessionUserDto notificationToNotificationSessionUserDto(Notification notification);
}
