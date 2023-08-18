package com.equipo5.feelflowapp.domain.users;

import com.equipo5.feelflowapp.domain.EnterPrise;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "admin_persona")
public class Admin extends User{

    @OneToOne(mappedBy = "admin")
    private EnterPrise enterPrise;
}
