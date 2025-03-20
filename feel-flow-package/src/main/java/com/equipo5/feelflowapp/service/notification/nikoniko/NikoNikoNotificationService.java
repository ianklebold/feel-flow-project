package com.equipo5.feelflowapp.service.notification.nikoniko;


import com.equipo5.feelflowapp.domain.modules.Survey;

public interface NikoNikoNotificationService {
    void sendNikoNikoNote(String note, Survey survey);
}
