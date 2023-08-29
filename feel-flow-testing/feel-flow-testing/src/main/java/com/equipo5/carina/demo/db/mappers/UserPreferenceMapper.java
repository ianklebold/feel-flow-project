package com.equipo5.carina.demo.db.mappers;

import com.equipo5.carina.demo.db.models.UserPreference;

public interface UserPreferenceMapper {

	void create(UserPreference userPreference);

	UserPreference findById(Long id);
}
