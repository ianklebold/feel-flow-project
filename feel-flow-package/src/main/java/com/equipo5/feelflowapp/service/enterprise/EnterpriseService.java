package com.equipo5.feelflowapp.service.enterprise;

import com.equipo5.feelflowapp.domain.EnterPrise;

import java.util.Optional;

public interface EnterpriseService {
    EnterPrise createEnterprise(EnterPrise enterPrise);

    Optional<EnterPrise> getEnterpriseByCurrentUser();
}
