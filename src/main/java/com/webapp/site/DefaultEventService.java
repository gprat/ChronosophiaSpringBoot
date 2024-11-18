package com.webapp.site;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.site.repositories.EventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.webapp.site.entities.Category;
import com.webapp.site.entities.City;
import com.webapp.site.entities.Date;
import com.webapp.site.entities.Event;
import com.webapp.site.entities.Figure;
import com.webapp.site.entities.User;

@Service
public class DefaultEventService implements EventService {

	@Inject EventRepository eventRepository;
	@Inject DateService dateService;
	@Inject UserService userService;
	@Inject CategoryService categoryService;
	@Inject CityService cityService;
	
	
	@Override
	@Transactional
	public List<Event> getAllEvents(){
		List<Event> list = new ArrayList<>();
		this.eventRepository.findAll().forEach(e->list.add(e));
		return list;
	}
	
	@Override
	@Transactional
	public Event getEvent(long id, String username){
		Event  event  = this.eventRepository.findById(id).get();
		if(event.getUser().getUsername().equals(username)) {
			return event;
		}
		else return null;
	}
	
	@Override
	@Transactional
	public EventForm getEventForm(Long id, String username){
		Event event = getEvent(id, username);
		EventForm eventForm = new EventForm();
		if(event!=null) {
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
			String categoryList="";
			for(Category c: event.getCategories()){
				categoryList=categoryList.concat(String.valueOf(c.getIdCategory())+",");
			}
			if(categoryList!="") categoryList.substring(1,categoryList.length()-1);
			eventForm.categories=categoryList;
		}
		return eventForm;
	}
	
	@Override
	public Event save(Event event){
		return this.eventRepository.save(event);
	}
	
	@Override
	public void delete(long id, String username){
		Event  event  = this.eventRepository.findById(id).get();
		if(event.getUser().getUsername().equals(username)) {
			this.eventRepository.deleteById(id);
		}
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
	public List<Event> getEventsById(List<Long> IdEvents, String username){
		List<Event> list = this.eventRepository.findByIdEventIn(IdEvents);
		for(Event e : list) {
			if (!e.getUser().getUsername().equals(username)) {
				list.remove(e);
			}
		}
		return list;
	}
	
	@Override
	public List<Event> getEventsByUsername(String username){
		return this.eventRepository.findByUser_username(username);
	}
	
	@Override
	public Event existsEvent(Date date, String name, User user) {
		List<Event> eventList = this.eventRepository.findByDateAndNameAndUser(date, name,user);
		if(eventList!=null&&!eventList.isEmpty()) {
			return eventList.get(0);
		}
		return null;
	}
	
	@Override
	public Event importEvent(JsonNode root, String username) {
		Event event = new Event();
		event.setName(root.path("name").asText());
		event.setUser(this.userService.findByUsername(username));
		JsonNode dateNode = root.path("date");
		//TO DO - Verifier le comportement avec des valeurs nulles
		event.setDate(this.dateService.setDate(dateNode.path("day").asInt(), dateNode.path("month").asInt(), dateNode.path("year").asInt()));
		Event eventExists = existsEvent(event.getDate(), event.getName(), event.getUser());
		if(eventExists==null) {
			List<Category> categoryList =  new ArrayList<>();
			for(JsonNode categoryNode : root.path("categories")) {
				String categoryName = categoryNode.path("name").asText();
				 categoryList.add(this.categoryService.setCategory(categoryName, username));
			}
			event.setCategories(categoryList);
			event.setDescription(root.path("description").asText());
			event.setUrl(root.path("url").asText());
			JsonNode cityNode = root.path("city");
			if (!cityNode.isMissingNode()) {
				event.setCity(this.cityService.setCity(cityNode, event.getUser()));
			}
			return save(event);
		}
		return eventExists;
	}
	
	@Override
	public List<Event> getEventsByUsernameAndIds(String username, List<Long> IdEvents){
		return this.eventRepository.findByUser_usernameAndIdEventIn(username, IdEvents);
	}
	
}
