package com.webapp.site;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.webapp.site.entities.Event;

public interface EventService {

	void delete(long id);

	void save(Event event);

	Event getEvent(long id);

	List<Event> getAllEvents();

	List<Event> getEventsByCategory(List<Long> categories, String username);

	EventForm getEventForm(Long id);

	List<Event> getEventsFiltered(List<Long> categories, List <Long> figures, List <Long> cities, List<Long> events, String username);

	List<Event> getEventsById(List<Long> IdEvents);
	
	List<Event> getEventsByUsername(String username);

}
