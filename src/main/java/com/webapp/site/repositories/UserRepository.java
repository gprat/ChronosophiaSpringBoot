package com.webapp.site.repositories;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.User;

public interface UserRepository extends CrudRepository<User,Long>{

	User findOneByUsername(String username);
}
