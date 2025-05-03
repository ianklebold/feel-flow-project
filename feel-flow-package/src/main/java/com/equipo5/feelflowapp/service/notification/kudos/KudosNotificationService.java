package com.equipo5.feelflowapp.service.notification.kudos;

import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.dto.notifications.NotificationKudosPanelDto;

import java.util.List;
import java.util.UUID;

public interface KudosNotificationService {
    void sendKudosNotificationToLeader(Badge badge);
    void sendKudosClosedNotificationToLeader(KudosModule kudosModule);
    List<NotificationKudosPanelDto> getNotificationKudosLeaders(UUID idLeader);
}
