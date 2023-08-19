package com.equipo5.feelflowapp.repository.users.admin;

import com.equipo5.feelflowapp.domain.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {}
