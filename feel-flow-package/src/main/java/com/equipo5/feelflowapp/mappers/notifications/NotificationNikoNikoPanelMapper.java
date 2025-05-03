package com.equipo5.feelflowapp.mappers.notifications;

import com.equipo5.feelflowapp.domain.notifications.NotificationNikoNikoLeader;
import com.equipo5.feelflowapp.dto.notifications.NotificationNikoNikoPanelDto;
import com.equipo5.feelflowapp.mappers.images.ImagesMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ImagesMapper.class)
public interface NotificationNikoNikoPanelMapper {
    @Mapping(source = "mediaImage", target = "imagesDto")
    @Mapping(source = "memberFrom", target = "regularUser")
    @Mapping(source = "body", target = "message")
    NotificationNikoNikoPanelDto notificationNikoNikoLeaderToNotificationNikoNikoPanelDto(NotificationNikoNikoLeader notificationNikoNikoLeader);
}
