package com.equipo5.feelflowapp.service.notification.impl;

import com.equipo5.feelflowapp.domain.notifications.Notification;
import com.equipo5.feelflowapp.dto.notifications.NotificationClientDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationSessionUserDto;
import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.mappers.notifications.NotificationMapper;
import com.equipo5.feelflowapp.mappers.notifications.NotificationSessionUserMapper;
import com.equipo5.feelflowapp.mappers.users.UserMapper;
import com.equipo5.feelflowapp.repository.notifications.NotificationRepository;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
            messagingTemplate.convertAndSend("/topic/" + notificationDto.uuidTeam().toString(), notification );
        }

    }

    @Override
    public List<NotificationSessionUserDto> getBadgesAvailableToSend(LocalDateTime from, LocalDateTime to, Integer max) {

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
}
