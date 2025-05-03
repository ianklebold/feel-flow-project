package com.equipo5.feelflowapp.repository.notifications;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.notifications.NotificationNikoNikoLeader;
import com.equipo5.feelflowapp.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationNikoNikoLeaderRepository extends JpaRepository<NotificationNikoNikoLeader, Long> {
    List<NotificationNikoNikoLeader> findAllByNotificationOwnerAndNotificationTypeEnumOrderByCreatedAtDesc(User notificationOwner, NotificationTypeEnum notificationTypeEnum);

}
