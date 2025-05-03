package com.equipo5.feelflowapp.domain.notifications;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "notificationleaderkudos_notification")
public class NotificationKudosLeader extends Notification{

    private String fromMember;

    private String toMember;


    @Builder
    public NotificationKudosLeader(Long id, String title, String body, boolean wasRead, boolean wasSeen, User notificationOwner, LocalDateTime createdAt, NotificationTypeEnum notificationTypeEnum, String fromMember, String toMember) {
        super(id, title, body, wasRead, wasSeen, notificationOwner, createdAt, notificationTypeEnum);
        this.fromMember = fromMember;
        this.toMember = toMember;
    }
}
