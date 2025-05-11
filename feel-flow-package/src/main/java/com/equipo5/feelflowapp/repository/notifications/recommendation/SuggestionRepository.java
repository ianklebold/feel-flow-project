package com.equipo5.feelflowapp.repository.notifications.recommendation;

import com.equipo5.feelflowapp.domain.notifications.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
