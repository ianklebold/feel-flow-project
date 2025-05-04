package com.equipo5.feelflowapp.service.notification.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.notifications.Notification;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.domain.users.TeamLeader;
import com.equipo5.feelflowapp.dto.notifications.NotificationClientDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationDto;
import com.equipo5.feelflowapp.dto.notifications.NotificationSessionUserDto;
import com.equipo5.feelflowapp.dto.users.UserDTO;
import com.equipo5.feelflowapp.mappers.notifications.NotificationMapper;
import com.equipo5.feelflowapp.mappers.notifications.NotificationSessionUserMapper;
import com.equipo5.feelflowapp.mappers.users.UserMapper;
import com.equipo5.feelflowapp.repository.notifications.NotificationRepository;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import com.equipo5.feelflowapp.service.team.TeamService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private final TeamService teamService;


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
            notificationEntity.setNotificationTypeEnum(NotificationTypeEnum.GENERAL);

            notificationEntity.setNotificationOwner( regularUser  );
            Notification notificationCreated = this.notificationRepository.save(notificationEntity);
        });
    }

    @Override
    public void sendNotificationModule(TeamLeader teamLeader, String body, String title) {
        Notification notificationEntity = new Notification();
        notificationEntity.setTitle(title);
        notificationEntity.setBody(body);
        notificationEntity.setWasRead( Boolean.FALSE );
        notificationEntity.setWasSeen( Boolean.FALSE );
        notificationEntity.setCreatedAt( LocalDateTime.now() );
        notificationEntity.setNotificationTypeEnum(NotificationTypeEnum.GENERAL);

        notificationEntity.setNotificationOwner( teamLeader  );
        Notification notificationCreated = this.notificationRepository.save(notificationEntity);
    }

    @Override
    public String generateBodyForOpenedModule(String nameModule, Timestamp dateAndTimeToPublish, Timestamp dateAndTimeToClose) {
        return "El modulo " + nameModule + " Se encuentra abierto y disponible " + "desde las " +
                formatToDayMonthYearHourMinute(LocalDateTime.of(dateAndTimeToPublish.getYear(),dateAndTimeToPublish.getMonth(),dateAndTimeToPublish.getDay(),dateAndTimeToPublish.getHours(),dateAndTimeToPublish.getMinutes()))
                + " y las " +
                formatToDayMonthYearHourMinute(LocalDateTime.of(dateAndTimeToClose.getYear(),dateAndTimeToClose.getMonth(),dateAndTimeToClose.getDay(),dateAndTimeToClose.getHours(),dateAndTimeToClose.getMinutes()));
    }

    public static String formatToDayMonthYearHourMinute(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    @Override
    public String generateBodyForCloseModule(String nameModule) {
        return "El modulo " + nameModule + " Se cerro con exito";
    }

    @Override
    public List<NotificationSessionUserDto> getNotificationsAvailableToSend(Integer max) {

        List<Team> teamList = teamService.getAllTeamsEntities();
        List<Notification> notifications;

        if(max == null || max <= 0){
            max = 10;
        }

        notifications = teamList.stream()
                        .flatMap(
                                team -> this.notificationRepository.findAllByNotificationOwnerAndNotificationTypeEnumOrderByCreatedAtDesc(team.getTeamLeader(),NotificationTypeEnum.GENERAL).stream()
                        ).toList();

        if (notifications.size() > max) {
               notifications = notifications.subList(0, max);
        }

        return notifications.stream()
                .map( notificationSessionUserMapper::notificationToNotificationSessionUserDto)
                .toList();
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

    @Override
    public void sendNotificationSurvey(Survey survey) {
        if (SurveyStateEnum.FINISHED.equals(survey.getSurveyStateEnum()) || SurveyStateEnum.CLOSED.equals(survey.getSurveyStateEnum()) ){
            Notification notification = new Notification();
            notification.setTitle("Encuesta 12 pasos de la felicidad cerrada");
            notification.setBody("El usuario " + survey.getRegularUser().getName() + "Completo la encuesta de 12 pasos de la felicidad");
            notification.setWasRead( Boolean.FALSE );
            notification.setWasSeen( Boolean.FALSE );
            notification.setCreatedAt( LocalDateTime.now() );
            notification.setNotificationTypeEnum(NotificationTypeEnum.GENERAL);
            notification.setNotificationOwner(survey.getRegularUser().getTeam().getTeamLeader());
            Notification notificationCreated = this.notificationRepository.save(notification);
        }
    }

    @Override
    public void sendNotificationSurveyAvailableNikoNiko(Module module) {
        module.getTeam().getRegularUsers().forEach(
                user -> {
                    Notification notification = new Notification();
                    notification.setTitle("Nueva encuesta de Niko Niko disponible");
                    notification.setBody("Durante el dia tendras disponible dos encuestas Niko Niko, al finalizar la jornada y al terminarla. Exitos!");
                    notification.setWasRead( Boolean.FALSE );
                    notification.setWasSeen( Boolean.FALSE );
                    notification.setCreatedAt( LocalDateTime.now() );
                    notification.setNotificationTypeEnum(NotificationTypeEnum.GENERAL);
                    notification.setNotificationOwner(user);
                    Notification notificationCreated = this.notificationRepository.save(notification);
                }
        );

    }



}
