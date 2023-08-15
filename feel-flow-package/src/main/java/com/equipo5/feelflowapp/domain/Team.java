package com.equipo5.feelflowapp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {
    private UUID uuid;
    private String name;
    private String descriptionProject;
    private List<RegularUser> regularUsers = new ArrayList<>();
}
