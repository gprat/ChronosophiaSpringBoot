package com.webapp.site.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.Chronology;
import com.webapp.site.entities.Figure;

public interface FigureRepository extends CrudRepository<Figure,Long> {
	
	List<Figure> findByCategories_NameAndRoles_NameAndUser_username(String categoryName, String roleName, String username);
	
	List<Figure> findByCategories_NameAndUser_username(String name, String username);
	
	List<Figure> findByRoles_NameAndUser_username(String name, String username);
	
	List<Figure> findByUser_username(String username);
	
	List<Figure> findByFirstNameAndLastNameAndBirthDate_yearAndUser_username(String firstName, String lastName, int year, String username);
	
	List<Figure> findByUser_usernameAndIdFigureIn(String username,List<Long> idFigure);
}
