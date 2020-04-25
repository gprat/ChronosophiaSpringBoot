package com.webapp.site;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.webapp.site.entities.Category;
import com.webapp.site.entities.User;
import com.webapp.site.repositories.CategoryRepository;
import com.webapp.site.repositories.UserRepository;


@Service
public class DefaultCategoryService implements CategoryService {

	@Inject
	CategoryRepository categorieRepository;
	
	@Inject
	UserRepository userRepository;
	
	@Override
	public List<Category> getAllCategories() {
		List<Category> list = new ArrayList<>();
		categorieRepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public Category getCategory(long id, String username) {
		Category category = categorieRepository.findById(id).get();
		if(category.getUser().getUsername().equals(username)) {
			return category;
		}
		return null;
	}

	@Override
	public void save(Category category) {
		categorieRepository.save(category);
	}

	@Override
	public void delete(long id, String username) {
		Category category = getCategory(id, username);
		if(category!=null) {
			categorieRepository.deleteById(id);
		}
	}
	
	@Override
	public List<Category> getCategoriesByUsername(String username){
		return categorieRepository.findByUser_username(username);
	}
	
	@Override
	public Category getCategoryByNameAndUsername(String name, String username) {
		return this.categorieRepository.getOneByNameAndUser_username(name, username);
	}
	
	@Override
	public Category setCategory(String categoryName, String username) {
		Category category = getCategoryByNameAndUsername(categoryName, username);
		 if (category == null) {
			 category = new Category();
			 category.setName(categoryName);
			 category.setUser(userRepository.findOneByUsername(username));
			 save(category);
			 category = getCategoryByNameAndUsername(categoryName, username);
		 }
		 return category;
	}

}
