package com.webapp.site;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.webapp.site.entities.Category;
import com.webapp.site.repositories.CategoryRepository;

@Service
public class DefaultCategoryService implements CategoryService {

	@Inject
	CategoryRepository categorieRepository;
	
	@Override
	public List<Category> getAllCategories() {
		List<Category> list = new ArrayList<>();
		categorieRepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public Category getCategory(long id) {
		return categorieRepository.findById(id).get();
	}

	@Override
	public void save(Category category) {
		categorieRepository.save(category);
	}

	@Override
	public void delete(long id) {
		categorieRepository.deleteById(id);
	}
	
	@Override
	public Category getCategory(String name){
		return categorieRepository.getOneByName(name);
	}
	
	@Override
	public List<Category> getCategoriesByUsername(String username){
		return categorieRepository.findByUser_username(username);
	}

}
