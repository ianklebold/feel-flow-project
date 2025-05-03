package com.equipo5.feelflowapp.domain.notifications;


import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.images.MediaImage;
import com.equipo5.feelflowapp.domain.users.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "notificationleadernikoniko_notification")
public class NotificationNikoNikoLeader extends Notification{

    private String memberFrom;

    @OneToOne(cascade = CascadeType.ALL)
    private MediaImage mediaImage;

    @Builder
    public NotificationNikoNikoLeader(Long id, String title, String body, boolean wasRead, boolean wasSeen, User notificationOwner, LocalDateTime createdAt, NotificationTypeEnum notificationTypeEnum, String memberFrom, MediaImage memberImage) {
        super(id, title, body, wasRead, wasSeen, notificationOwner, createdAt, notificationTypeEnum);
        this.memberFrom = memberFrom;
        this.mediaImage = memberImage;
    }
}
