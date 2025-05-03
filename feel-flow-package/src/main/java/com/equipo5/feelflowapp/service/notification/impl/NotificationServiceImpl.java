package com.equipo5.feelflowapp.service.notification.impl;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.modules.kudos.Badge;
import com.equipo5.feelflowapp.domain.notifications.Notification;
import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.notifications.NotificationClientDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationKudosPanelDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationSessionUserDto;
import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.mappers.notifications.NotificationMapper;
import com.equipo5.feelflowapp.mappers.notifications.NotificationSessionUserMapper;
import com.equipo5.feelflowapp.mappers.users.UserMapper;
import com.equipo5.feelflowapp.repository.notifications.NotificationRepository;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    private final NotificationMapper notificationMapper;

    private final NotificationSessionUserMapper notificationSessionUserMapper;

    private final NotificationRepository notificationRepository;

    private final UserService userService;

    private final UserMapper userMapper;


    @Override
    public void sendNotificationToKudosPanel(Badge badge) {
        Optional<UserDTO> optionalUserDTO = userService.getSessionUser();
        TeamLeader teamLeader = badge.getBadgeOwner().getTeam().getTeamLeader();

        if(optionalUserDTO.isPresent()) {
            Notification notificationEntity = Notification.builder()
                    .title("Envio de Kudos")
                    .body("Se envio un Kudos por parte la fecha :" + badge.getAwardedDate() + " Al miembro : " + badge.getBadgeOwner().getName())
                    .wasSeen(false)
                    .wasRead(false)
                    .notificationOwner( this.userMapper.userDtoToUser( optionalUserDTO.get() ) )
                    .notificationTypeEnum(NotificationTypeEnum.KUDOS)
                    .createdAt(LocalDateTime.now())
                    .build();
            this.notificationRepository.save(notificationEntity);
        }
    }

    @Override
    public void sendNotification(NotificationClientDto notificationDto) {
        //Guardar en base de datos la notificacion
        Optional<UserDTO> optionalUserDTO = userService.getSessionUser();

        if(optionalUserDTO.isPresent()) {
            Notification notificationEntity = notificationMapper.notificationClientDtoToNotification( notificationDto );
            notificationEntity.setWasRead( Boolean.FALSE );
            notificationEntity.setWasSeen( Boolean.FALSE );
            notificationEntity.setCreatedAt( LocalDateTime.now() );

            notificationEntity.setNotificationOwner( this.userMapper.userDtoToUser( optionalUserDTO.get() )  );
            this.notificationRepository.save(notificationEntity);


            NotificationDto notification = notificationMapper.notificationClientDtoToNotificationDto( notificationDto );
            messagingTemplate.convertAndSend("/topic/" + notificationDto.uuid().toString(), notification );
        }

    }

    @Override
    public void sendNotificationModule(List<RegularUser> users, String body, String title) {
        users.forEach(regularUser -> {
            Notification notificationEntity = new Notification();
            notificationEntity.setTitle(title);
            notificationEntity.setBody(body);
            notificationEntity.setWasRead( Boolean.FALSE );
            notificationEntity.setWasSeen( Boolean.FALSE );
            notificationEntity.setCreatedAt( LocalDateTime.now() );
            notificationEntity.setNotificationTypeEnum(NotificationTypeEnum.KUDOS);

            notificationEntity.setNotificationOwner( regularUser  );
            Notification notificationCreated = this.notificationRepository.save(notificationEntity);

            NotificationDto notification = notificationMapper.notificationToNotificationDto( notificationEntity );

            messagingTemplate.convertAndSend("/topic/" + notificationCreated.getId().toString(), notification );
        });
    }

    @Override
    public String generateBodyForOpenedModule(String nameModule, Timestamp dateAndTimeToPublish, Timestamp dateAndTimeToClose) {
        return "El modulo " + nameModule + "Se encuentra abierto y disponible " + "desde las " +
                LocalDateTime.of(dateAndTimeToPublish.getYear(),dateAndTimeToPublish.getMonth(),dateAndTimeToPublish.getDay(),dateAndTimeToPublish.getHours(),dateAndTimeToPublish.getMinutes())
                + " y las " +
                LocalDateTime.of(dateAndTimeToClose.getYear(),dateAndTimeToClose.getMonth(),dateAndTimeToClose.getDay(),dateAndTimeToClose.getHours(),dateAndTimeToClose.getMinutes());
    }

    @Override
    public String generateBodyForCloseModule(String nameModule) {
        return "El modulo" + nameModule + "se cerro con exito";
    }

    @Override
    public List<NotificationSessionUserDto> getNotificationsAvailableToSend(LocalDateTime from, LocalDateTime to, Integer max) {

        Optional<UserDTO> optionalUserDTO = userService.getSessionUser();
        if(optionalUserDTO.isPresent()) {
            List<Notification> notifications = new ArrayList<>();
            if (from != null && to != null && from.isBefore(to)) {
                notifications = notificationRepository.findAllByNotificationOwnerAndCreatedAtBetween(
                        this.userMapper.userDtoToUser( optionalUserDTO.get() ),
                        from,
                        to);

            }else {
                notifications = notificationRepository.findAllByNotificationOwnerAndCreatedAtBetween(
                        this.userMapper.userDtoToUser( optionalUserDTO.get() ),
                        LocalDateTime.now().minusDays(7),
                        LocalDateTime.now()
                );
            }

            if(max == null || max <= 0){
                max = 10;
            }

            if (notifications.size() > max) {
                notifications = notifications.subList(0, max);
            }

            return notifications.stream()
                    .map( notificationSessionUserMapper::notificationToNotificationSessionUserDto)
                    .toList();
        }

        return List.of();
    }

    @Override
    public List<NotificationDto> getNotificationsByUser(NotificationTypeEnum notificationType) {
        Optional<UserDTO> optionalUserDTO = userService.getSessionUser();
        if(optionalUserDTO.isPresent()) {
            List<Notification> notifications;
            if (notificationType == null) {
                notificationType = NotificationTypeEnum.GENERAL;
            }
            notifications = notificationRepository.findAllByNotificationOwnerAndNotificationTypeEnumOrderByCreatedAtDesc(
                    this.userMapper.userDtoToUser( optionalUserDTO.get() ),
                    notificationType
            );
            return notifications.stream().map(notificationMapper::notificationToNotificationDto).toList();
        }

        return List.of();
    }
}
