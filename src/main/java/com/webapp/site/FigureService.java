package com.webapp.site;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.webapp.site.entities.Figure;

public interface FigureService {

	List<Figure> getAllFigures();
	
	List<Figure> getFiguresByUsername(String username);
	
	Figure getFigure(long id);
	
	void save(Figure figure);
	
	void delete(long id);
	
	List<Figure> getFiguresByCategoryAndRole(String category,String role, String username);

	FigureForm getFigureForm(Long id);
}
