package com.equipo5.feelflowapp.repository.notifications;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.notifications.NotificationKudosLeader;
import com.equipo5.feelflowapp.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationKudosLeaderRepository extends JpaRepository<NotificationKudosLeader, Long> {
    List<NotificationKudosLeader> findAllByNotificationOwnerAndNotificationTypeEnumOrderByCreatedAtDesc(User notificationOwner, NotificationTypeEnum notificationType);
}
