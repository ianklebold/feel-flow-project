package com.equipo5.feelflowapp.repository.enterprise;

import com.equipo5.feelflowapp.domain.EnterPrise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnterpriseRepository extends JpaRepository<EnterPrise, UUID> {}
