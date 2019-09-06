package com.webapp.site.repositories;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.PersistentLogin;
import java.lang.String;
import java.util.List;;

public interface PersistentLoginRepository extends CrudRepository<PersistentLogin,String> {
	
	PersistentLogin findOneByUsername(String username);
}
