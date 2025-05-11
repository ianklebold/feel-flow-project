package com.equipo5.feelflowapp.repository.notifications.recommendation;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.notifications.Recommendation;
import com.equipo5.feelflowapp.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findAllByNotificationOwnerAndNotificationTypeEnumOrderByCreatedAtDesc(User user, NotificationTypeEnum notificationTypeEnum);
}
