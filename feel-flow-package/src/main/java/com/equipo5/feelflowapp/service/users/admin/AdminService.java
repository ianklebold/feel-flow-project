package com.equipo5.feelflowapp.service.users.admin;

import com.equipo5.feelflowapp.domain.users.Admin;
import com.equipo5.feelflowapp.dto.users.admin.AdminDTO;

public interface AdminService {
    Admin createAdmin(AdminDTO admin);
}
