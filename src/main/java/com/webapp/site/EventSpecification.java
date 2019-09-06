package com.webapp.site;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.webapp.site.entities.City;
import com.webapp.site.entities.Event;
import com.webapp.site.entities.Category;
import com.webapp.site.entities.Figure;

public class EventSpecification implements Specification<Event>{
	private EventFilter criteria;
	
	public EventSpecification(EventFilter ef){
		criteria=ef;
	}
	
	@Override
	public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		
		
		final List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(criteria.getCategories()!=null&&criteria.getCategories().size()>0){
			Join categoryJoin = root.join("categories");
			Path<Integer> idCategory = categoryJoin.get("idCategory");
			predicates.add(idCategory.in(criteria.getCategories()));
		}
		if(criteria.getFigures()!=null&&criteria.getFigures().size()>0){
			Join figureJoin = root.join("figures");
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
			Join userJoin = root.join("user");
			Path<String> username = userJoin.get("username");
			predicates.add(username.in(criteria.getUsername()));
 		}
		query.distinct(true);
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
