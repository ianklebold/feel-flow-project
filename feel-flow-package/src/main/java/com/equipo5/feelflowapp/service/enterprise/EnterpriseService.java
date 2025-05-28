package com.equipo5.feelflowapp.service.enterprise;

import com.equipo5.feelflowapp.domain.EnterPrise;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;

public interface EnterpriseService {
    EnterPrise createEnterprise(EnterPrise enterPrise);

    Optional<EnterPrise> getEnterpriseByCurrentUser();

    Optional<EnterPrise> getEnterpriseByCurrentUser(GrantedAuthority role);
}
