package com.webapp.site;

import java.util.List;

import com.webapp.site.entities.Figure;

public interface FigureService {

	List<Figure> getAllFigures();
	
	List<Figure> getFiguresByUsername(String username);
	
	Figure getFigure(long id, String username);
	
	void save(Figure figure);
	
	void delete(long id, String username);
	
	List<Figure> getFiguresByCategoryAndRole(String category,String role, String username);

	FigureForm getFigureForm(Long id, String username);

	boolean existsFigure(String firstName, String lastName, int year, String username);

	List<Figure> getFiguresByUsernameAndIds(String username, List<Long> ids);
}
