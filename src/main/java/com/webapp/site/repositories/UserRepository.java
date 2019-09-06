package com.webapp.site.repositories;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.User;
import java.lang.String;
import java.util.List;

public interface UserRepository extends CrudRepository<User,Long>{

	User findOneByUsername(String username);
}
