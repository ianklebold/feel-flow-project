package com.equipo5.feelflowapp.mappers.notifications;

import com.equipo5.feelflowapp.domain.notifications.NotificationKudosLeader;
import com.equipo5.feelflowapp.dto.notifications.NotificationKudosPanelDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper
public interface NotificationKudosPanelMapper {

    @Mapping(source = "from", target = "from")
    @Mapping(source = "to", target = "to")
    @Mapping(source = "body", target = "badgeName")
    @Mapping(source = "createdAt", target = "creationDate", qualifiedByName = "creationDateMapper")
    NotificationKudosPanelDto notificationKudosLeaderToNotificationKudosPanelDto(NotificationKudosLeader notificationKudosLeader);

    @Named("creationDateMapper")
    public static LocalDate creationDateMapper(LocalDateTime dateTime) {
        return dateTime.toLocalDate();
    }
}
