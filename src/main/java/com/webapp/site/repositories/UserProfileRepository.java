package com.webapp.site.repositories;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile,Long> {

	UserProfile findOneByName(String name);
}
