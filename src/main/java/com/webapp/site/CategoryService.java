package com.webapp.site;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.webapp.site.entities.Category;

public interface CategoryService {
	
	@Transactional
	List<Category> getAllCategories();
	
	Category getCategory(long id);
	
	void save(Category category);
	
	void delete(long id);
	
	Category getCategory(String name);

	List<Category> getCategoriesByUsername(String username);
}
