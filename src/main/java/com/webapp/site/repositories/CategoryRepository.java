package com.webapp.site.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	Category getOneByName(String name);
	
	List<Category> findByUser_username(String username);

}
