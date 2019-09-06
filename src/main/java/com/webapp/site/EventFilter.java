package com.webapp.site;

import java.util.List;

public class EventFilter {

	private List<Long> figures;
	private List<Long> categories;
	private List<Long> cities;
	private List<Long> eventsToExclude;
	private String username;
	
	public List<Long> getFigures() {
		return figures;
	}
	public void setFigures(List<Long> figures) {
		this.figures = figures;
	}
	public List<Long> getCategories() {
		return categories;
	}
	public void setCategories(List<Long> categories) {
		this.categories = categories;
	}
	public List<Long> getCities() {
		return cities;
	}
	public void setCities(List<Long> cities) {
		this.cities = cities;
	}
	public List<Long> getEventsToExclude() {
		return eventsToExclude;
	}
	public void setEventsToExclude(List<Long> eventsToExclude) {
		this.eventsToExclude = eventsToExclude;
	}
	public String getUsername() {
		return username;
	}
	public void setusername(String username) {
		this.username = username;
	}
}
