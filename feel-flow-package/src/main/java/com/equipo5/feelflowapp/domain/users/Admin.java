package com.equipo5.feelflowapp.domain.users;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.domain.images.MediaImage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "admin_persona")
public class Admin extends User{

    @OneToOne(mappedBy = "admin",cascade = CascadeType.ALL)
    private EnterPrise enterPrise;

    @Builder
    public Admin(UUID uuid, String name, String surname, String username, String password, String country, String phoneNumber, String description, List<Authority> authorities, MediaImage mediaImage, EnterPrise enterPrise) {
        super(uuid, name, surname, username, password, country, phoneNumber, description, authorities, mediaImage);
        this.enterPrise = enterPrise;
    }
}
