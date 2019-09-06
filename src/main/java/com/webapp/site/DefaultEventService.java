package com.webapp.site;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.site.repositories.EventRepository;
import com.webapp.site.entities.Category;
import com.webapp.site.entities.Event;
import com.webapp.site.entities.Figure;

@Service
public class DefaultEventService implements EventService {

	@Inject EventRepository eventRepository;
	
	@Override
	@Transactional
	public List<Event> getAllEvents(){
		List<Event> list = new ArrayList<>();
		this.eventRepository.findAll().forEach(e->list.add(e));
		return list;
	}
	
	@Override
	@Transactional
	public Event getEvent(long id){
		return this.eventRepository.findById(id).get();
	}
	
	@Override
	@Transactional
	public EventForm getEventForm(Long id){
		Event event = getEvent(id);
		EventForm eventForm = new EventForm();
		eventForm.id=event.getIdEvent();
		if(event.getDate()!=null){
			eventForm.day=event.getDate().getDay();
			eventForm.month=event.getDate().getMonth();
			eventForm.year=event.getDate().getYear();
		}
		eventForm.name=event.getName();
		eventForm.url=event.getUrl();
		eventForm.description=event.getDescription();
		if(event.getCity()!=null){
			eventForm.idCity=String.valueOf(event.getCity().getIdCity());
		}
		String figureList="";
		for(Figure f : event.getFigures()){
			figureList=figureList.concat(String.valueOf(f.getIdFigure())+",");
		}
		if(figureList!="") figureList.substring(1,figureList.length()-1);
		eventForm.figures=figureList;
		String categoryList="";
		for(Category c: event.getCategories()){
			categoryList=categoryList.concat(String.valueOf(c.getIdCategory())+",");
		}
		if(categoryList!="") categoryList.substring(1,categoryList.length()-1);
		eventForm.categories=categoryList;
		return eventForm;
	}
	
	@Override
	public void save(Event event){
		this.eventRepository.save(event);
	}
	
	@Override
	public void delete(long id){
		this.eventRepository.deleteById(id);
	}
	
	@Override
	public List<Event> getEventsByCategory(List<Long> categories, String username){
		List<Event> list = new ArrayList<>();
		if(categories.size()>0){
			this.eventRepository.findDistinctByCategories_IdCategoryInAndUser_username(categories, username).forEach(e->list.add(e));
		}
		else{
			this.eventRepository.findAll().forEach(e->list.add(e));
		}
		return list;
	}
	
	@Override
	public List<Event> getEventsFiltered(List<Long> categories, List <Long> figures,List <Long> cities, List<Long> events, String username){
		List<Event> list = new ArrayList<>();
		EventFilter ef = new EventFilter();
		ef.setCategories(categories);
		ef.setEventsToExclude(events);;
		ef.setFigures(figures);
		ef.setCities(cities);
		ef.setusername(username);
		this.eventRepository.findAll(new EventSpecification(ef)).forEach(e->list.add(e));
		return list;
	}
	
	@Override
	public List<Event> getEventsById(List<Long> IdEvents){
		List<Event> list = new ArrayList<>();
		this.eventRepository.findByIdEventIn(IdEvents).forEach(e->list.add(e));
		return list;
	}
	
	@Override
	public List<Event> getEventsByUsername(String username){
		return this.eventRepository.findByUser_username(username);
	}
	
}
