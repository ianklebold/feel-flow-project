package com.equipo5.feelflowapp.domain.notifications;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "notificationleaderkudos_notification")
public class NotificationKudosLeader extends Notification{
    private String from;
    private String to;

    @Builder
    public NotificationKudosLeader(String from, String to) {
        this.from = from;
        this.to = to;
    }
}
