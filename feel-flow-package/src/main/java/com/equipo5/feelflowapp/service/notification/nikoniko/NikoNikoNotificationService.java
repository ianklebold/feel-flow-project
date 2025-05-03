package com.equipo5.feelflowapp.service.notification.nikoniko;


import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.dto.notifications.NotificationNikoNikoPanelDto;

import java.util.List;
import java.util.UUID;

public interface NikoNikoNotificationService {
    void sendNikoNikoNote(String note, Survey survey);

    List<NotificationNikoNikoPanelDto> getNotificationKudosLeaders(UUID idLeader);
}
