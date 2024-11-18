package com.webapp.site;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.webapp.site.entities.Event;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.webapp.site.entities.Category;
import com.webapp.site.entities.Figure;	
import com.webapp.site.entities.User;

public class EventSpecification implements Specification<Event>{
	private EventFilter criteria;
	
	public EventSpecification(EventFilter ef){
		criteria=ef;
	}
	
	@Override
	public Predicate toPredicate(@NonNull Root<Event> root,@Nullable CriteriaQuery<?> query,
			@NonNull CriteriaBuilder cb) {
		
		
		final List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(criteria.getCategories()!=null&&criteria.getCategories().size()>0){
			Join<Figure,Category> categoryJoin = root.join("categories");
			Path<Integer> idCategory = categoryJoin.get("idCategory");
			predicates.add(idCategory.in(criteria.getCategories()));
		}
		if(criteria.getFigures()!=null&&criteria.getFigures().size()>0){
			Join<Event,Figure> figureJoin = root.join("figures");
			Path<Integer> idFigure = figureJoin.get("idFigure");
			predicates.add(idFigure.in(criteria.getFigures()));
		}
		if(criteria.getCities()!=null&&criteria.getCities().size()>0){
			Path<Integer> idCity = root.get("city");
			predicates.add(idCity.in(criteria.getCities()));
		}
		if(criteria.getEventsToExclude()!=null&&criteria.getEventsToExclude().size()>0){
			Path<Integer> idEvent = root.get("idEvent");
			predicates.add(idEvent.in(criteria.getEventsToExclude()).not());
		}
		if(criteria.getUsername()!=null&&criteria.getUsername()!=""){
			Join<Event,User> userJoin = root.join("user");
			Path<String> username = userJoin.get("username");
			predicates.add(username.in(criteria.getUsername()));
 		}
		if(query!=null){
			query.distinct(true);
		}
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
