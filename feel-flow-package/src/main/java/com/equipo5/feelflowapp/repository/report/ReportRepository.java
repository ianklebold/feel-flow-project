package com.equipo5.feelflowapp.repository.report;

import com.equipo5.feelflowapp.domain.modules.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
