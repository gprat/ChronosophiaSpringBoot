package com.webapp.site;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.webapp.site.entities.Date;
import com.webapp.site.entities.Event;
import com.webapp.site.entities.User;

public interface EventService {

	void delete(long id, String username);

	Event save(Event event);

	Event getEvent(long id, String username);

	List<Event> getAllEvents();

	List<Event> getEventsByCategory(List<Long> categories, String username);

	EventForm getEventForm(Long id, String username);

	List<Event> getEventsFiltered(List<Long> categories, List <Long> figures, List <Long> cities, List<Long> events, String username);

	List<Event> getEventsById(List<Long> IdEvents, String username);
	
	List<Event> getEventsByUsername(String username);

	Event existsEvent(Date date, String name, User user);

	Event importEvent(JsonNode root, String username);

	List<Event> getEventsByUsernameAndIds(String username, List<Long> IdEvents);

}
