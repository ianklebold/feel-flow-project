package com.equipo5.feelflowapp.repository.notifications;

import com.equipo5.feelflowapp.domain.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
