package com.equipo5.feelflowapp.repository.notifications;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.notifications.Notification;
import com.equipo5.feelflowapp.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByNotificationOwnerAndCreatedAtBetween(User owner, LocalDateTime from, LocalDateTime to);
    List<Notification> findAllByNotificationOwnerAndNotificationTypeEnumOrderByCreatedAtDesc(User owner, NotificationTypeEnum notificationTypeEnum);
}
