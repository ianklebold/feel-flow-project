package com.equipo5.feelflowapp.domain.users;

import com.equipo5.feelflowapp.domain.EnterPrise;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin extends User{
    private EnterPrise enterPrise;
}
