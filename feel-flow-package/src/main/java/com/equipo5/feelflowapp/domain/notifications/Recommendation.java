package com.equipo5.feelflowapp.domain.notifications;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "recommendation_notification")
public class Recommendation extends Notification{

    @OneToMany
    private List<Suggestion> suggestion;

    @Builder
    public Recommendation(Long id, String title, String body, boolean wasRead, boolean wasSeen, User notificationOwner, LocalDateTime createdAt, NotificationTypeEnum notificationTypeEnum, List<Suggestion> suggestion) {
        super(id, title, body, wasRead, wasSeen, notificationOwner, createdAt, notificationTypeEnum);
        this.suggestion = suggestion;
    }

}
