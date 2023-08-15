package com.equipo5.feelflowapp.domain;


import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;

import java.util.UUID;

public class User {
    private UUID uuid;
    private String name;
    private String email;

    private EnterPrise enterPrise;
    private Team team;
}
