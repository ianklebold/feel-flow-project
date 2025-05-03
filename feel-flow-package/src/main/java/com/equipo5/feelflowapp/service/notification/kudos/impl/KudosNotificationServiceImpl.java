package com.equipo5.feelflowapp.service.notification.kudos.impl;

import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.notifications.Notification;
import com.equipo5.feelflowapp.domain.notifications.NotificationKudosLeader;
import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.dto.notifications.NotificationKudosPanelDto;
import com.equipo5.feelflowapp.mappers.notifications.NotificationKudosPanelMapper;
import com.equipo5.feelflowapp.repository.notifications.NotificationKudosLeaderRepository;
import com.equipo5.feelflowapp.repository.notifications.NotificationRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.service.notification.kudos.KudosNotificationService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class KudosNotificationServiceImpl implements KudosNotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationKudosLeaderRepository notificationKudosLeaderRepository;

    protected final UserService userService;

    protected final UserRepository userRepository;

    protected final NotificationKudosPanelMapper notificationKudosPanelMapper;



    @Override
    public void sendKudosNotificationToLeader(Badge badge) {

        var username = userService.getUsernameByCurrentUser();
        var regularUser = userRepository.findByUsername(username);

        NotificationKudosLeader notificationKudosLeader = new NotificationKudosLeader();
        notificationKudosLeader.setTitle("Ultimos Kudos");
        notificationKudosLeader.setBody( getKudosType(badge.getBadgeName()) );
        notificationKudosLeader.setFromMember(regularUser.get().getName()  + " " + regularUser.get().getSurname() );
        notificationKudosLeader.setToMember(badge.getBadgeOwner().getName() + " " + badge.getBadgeOwner().getSurname());
        notificationKudosLeader.setCreatedAt(LocalDateTime.now());
        notificationKudosLeader.setWasRead(false);
        notificationKudosLeader.setWasSeen(false);
        notificationKudosLeader.setNotificationTypeEnum(NotificationTypeEnum.PANEL);
        notificationKudosLeader.setNotificationOwner(badge.getBadgeOwner().getTeam().getTeamLeader());
        notificationKudosLeaderRepository.save(notificationKudosLeader);
    }

    @Override
    public void sendKudosClosedNotificationToLeader(KudosModule kudosModule) {
        Notification notification = new Notification();
        notification.setTitle("Cierre de modulo Kudos");
        notification.setBody("Se ha cerrado el modulo de Kudos");
        notification.setWasRead(false);
        notification.setWasSeen(false);
        notification.setNotificationTypeEnum(NotificationTypeEnum.GENERAL);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setNotificationOwner(kudosModule.getTeam().getTeamLeader());
        notificationRepository.save(notification);
    }

    private String getKudosType(BadgeName badgeName){
        return switch (badgeName){
            case MANOS_AMIGAS -> "Manos Amigas";
            case ENERGIA_POSITIVA -> "Energia Positiva";
            case RESOLUTOR_ESTRELLA -> "Resolutor Estrella";
            case MAESTRO_DEL_DETALLE -> "Maestro Del Detalle";
        };
    }

    @Override
    public List<NotificationKudosPanelDto> getNotificationKudosLeaders(UUID idLeader) {

        Optional<User> user = userRepository.findById(idLeader);

        List<NotificationKudosPanelDto> notificationKudosLeaders = user.map(value -> notificationKudosLeaderRepository.findAllByNotificationOwnerAndNotificationTypeEnumOrderByCreatedAtDesc(value, NotificationTypeEnum.PANEL)
                .stream()
                .map(notificationKudosPanelMapper::notificationKudosLeaderToNotificationKudosPanelDto)
                .toList())
                .orElseGet(List::of);

        if (notificationKudosLeaders.size() > 10){
            notificationKudosLeaders = notificationKudosLeaders.subList(0, 10);
        }
        return notificationKudosLeaders;
    }

}
