package com.webapp.site;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.webapp.site.entities.Category;

public interface CategoryService {
	
	@Transactional
	List<Category> getAllCategories();
	
	Category getCategory(long id, String username);
	
	void save(Category category);
	
	void delete(long id, String username);

	List<Category> getCategoriesByUsername(String username);

	Category getCategoryByNameAndUsername(String name, String username);

	Category setCategory(String categoryName, String username);
}
