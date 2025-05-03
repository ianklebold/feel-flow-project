package com.equipo5.feelflowapp.service.notification.nikoniko.impl;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.notifications.NotificationKudosLeader;
import com.equipo5.feelflowapp.domain.notifications.NotificationNikoNikoLeader;
import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.dto.notifications.NotificationNikoNikoPanelDto;
import com.equipo5.feelflowapp.mappers.notifications.NotificationNikoNikoPanelMapper;
import com.equipo5.feelflowapp.repository.notifications.NotificationNikoNikoLeaderRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.service.notification.nikoniko.NikoNikoNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class NikoNikoNotificationServiceImpl implements NikoNikoNotificationService {

    private final NotificationNikoNikoLeaderRepository notificationNikoNikoLeaderRepository;
    private final UserRepository userRepository;
    private final NotificationNikoNikoPanelMapper notificationNikoNikoPanelMapper;

    @Override
    public void sendNikoNikoNote(String note, Survey survey) {
        if (!note.isEmpty()){
            NotificationNikoNikoLeader nikoNikoLeader = new NotificationNikoNikoLeader();
            nikoNikoLeader.setMemberFrom(survey.getRegularUser().getName() + " " + survey.getRegularUser().getSurname());
            nikoNikoLeader.setTitle("Ultimas Notas de Niko Niko");
            nikoNikoLeader.setBody(note);
            nikoNikoLeader.setWasRead(false);
            nikoNikoLeader.setWasSeen(false);
            nikoNikoLeader.setNotificationTypeEnum(NotificationTypeEnum.PANEL);
            nikoNikoLeader.setCreatedAt(LocalDateTime.now());
            nikoNikoLeader.setNotificationOwner(survey.getSurveyModule().getTeam().getTeamLeader());
            nikoNikoLeader.setMediaImage(survey.getRegularUser().getMediaImage());
            notificationNikoNikoLeaderRepository.save(nikoNikoLeader);
        }
    }

    @Override
    public List<NotificationNikoNikoPanelDto> getNotificationKudosLeaders(UUID idLeader) {
        Optional<User> user = userRepository.findById(idLeader);

        List<NotificationNikoNikoPanelDto> notificationNikoNikoPanelDtos = user.map(value -> notificationNikoNikoLeaderRepository.findAllByNotificationOwnerAndNotificationTypeEnumOrderByCreatedAtDesc(value, NotificationTypeEnum.PANEL)
                        .stream()
                        .map(notificationNikoNikoPanelMapper::notificationNikoNikoLeaderToNotificationNikoNikoPanelDto)
                        .toList())
                .orElseGet(List::of);

        if (notificationNikoNikoPanelDtos.size() > 10){
            return notificationNikoNikoPanelDtos.subList(0, 10);
        }
        return notificationNikoNikoPanelDtos;
    }


}
