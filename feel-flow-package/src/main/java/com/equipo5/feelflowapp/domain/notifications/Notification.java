package com.equipo5.feelflowapp.domain.notifications;

import com.equipo5.feelflowapp.domain.enumerations.notification.NotificationTypeEnum;
import com.equipo5.feelflowapp.domain.users.User;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;


@Entity(name = "notification")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    private boolean wasRead;

    private boolean wasSeen;

    @ManyToOne(fetch = FetchType.LAZY)
    private User notificationOwner;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private NotificationTypeEnum notificationTypeEnum;
}
