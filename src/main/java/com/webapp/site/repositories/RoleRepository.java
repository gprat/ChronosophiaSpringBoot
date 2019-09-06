package com.webapp.site.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.Role;

public interface RoleRepository extends CrudRepository<Role,Long>{

	Role getOneByName (String name);
	
	List<Role> findByUser_username(String username);
}
