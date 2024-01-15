package com.equipo5.feelflowapp.repository.activity;

import com.equipo5.feelflowapp.domain.modules.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
}
