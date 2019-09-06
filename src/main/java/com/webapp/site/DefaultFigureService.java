package com.webapp.site;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.site.entities.Category;
import com.webapp.site.entities.Figure;
import com.webapp.site.entities.Role;
import com.webapp.site.repositories.FigureRepository;

@Service
public class DefaultFigureService implements FigureService {

	@Inject FigureRepository figureRepository;
	
	@Override
	public List<Figure> getAllFigures() {
		List<Figure> list = new ArrayList<>();
		this.figureRepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public Figure getFigure(long id) {
		return this.figureRepository.findById(id).get();
	}

	@Override
	public void save(Figure figure) {
		this.figureRepository.save(figure);
	}

	@Override
	public void delete(long id) {
		this.figureRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public FigureForm getFigureForm(Long id){
		Figure figure = getFigure(id);
		FigureForm figureForm = new FigureForm();
		figureForm.id=figure.getIdFigure();
		if(figure.getBirthDate()!=null){
			figureForm.dayOfBirth=figure.getBirthDate().getDay();
			figureForm.monthOfBirth=figure.getBirthDate().getMonth();
			figureForm.yearOfBirth=figure.getBirthDate().getYear();
		}
		if(figure.getDeathDate()!=null){
			figureForm.dayOfDeath=figure.getDeathDate().getDay();
			figureForm.monthOfDeath=figure.getDeathDate().getMonth();
			figureForm.yearOfDeath=figure.getDeathDate().getYear();
		}
		figureForm.firstName=figure.getFirstName();
		figureForm.lastName=figure.getLastName();
		String categoryList="";
		for(Category c: figure.getCategories()){
			categoryList=categoryList.concat(String.valueOf(c.getIdCategory())+",");
		}
		if(categoryList!="") categoryList.substring(1,categoryList.length()-1);
		figureForm.categories=categoryList;
		String roleList="";
		for(Role r: figure.getRoles()){
			roleList=roleList.concat(String.valueOf(r.getIdRole())+",");
		}
		if(roleList!="") roleList.substring(1,roleList.length()-1);
		figureForm.roles=roleList;
		figureForm.biography=figure.getBiography();
		figureForm.url=figure.getUrl();
		return figureForm;
	}
	
	@Override
	public List<Figure> getFiguresByCategoryAndRole(String category,String role, String username){
		List<Figure> list = new ArrayList<>();
		if(category!=""){
			if(role!=""){
				this.figureRepository.findByCategories_NameAndRoles_NameAndUser_username(category, role, username).forEach(e -> list.add(e));
			}
			else this.figureRepository.findByCategories_NameAndUser_username(category, username).forEach(e -> list.add(e));
		}
		else{
			if(role!=""){
				this.figureRepository.findByRoles_NameAndUser_username(role, username).forEach(e -> list.add(e));
			}
			else this.figureRepository.findAll().forEach(e -> list.add(e));
		}
		return list;
	}
	
	@Override
	public List<Figure>  getFiguresByUsername(String username){
		return this.figureRepository.findByUser_username(username);
	}
}
