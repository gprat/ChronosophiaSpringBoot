package com.webapp.site.repositories;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

import com.webapp.site.entities.User;

public interface UserRepository extends CrudRepository<User,Long>{

	User findOneByUsername(String username);

	Optional<User> findByUsername(String username);
}
