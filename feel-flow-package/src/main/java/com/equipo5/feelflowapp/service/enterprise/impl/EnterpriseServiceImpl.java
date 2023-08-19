package com.equipo5.feelflowapp.service.enterprise.impl;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.repository.enterprise.EnterpriseRepository;
import com.equipo5.feelflowapp.service.enterprise.EnterpriseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    @Override
    public EnterPrise createEnterprise(EnterPrise enterPrise) {

        return enterpriseRepository.save(enterPrise);
    }
}
