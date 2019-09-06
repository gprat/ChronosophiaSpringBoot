package com.webapp.site;

public class SelectEventForm {
	
	String categories;
	
	String figures;
	
	String cities;
	
	String eventsToExclude;

	public String getFigures() {
		return figures;
	}

	public void setFigures(String figures) {
		this.figures = figures;
	}
	
	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}

	public String getEventsToExclude() {
		return eventsToExclude;
	}

	public void setEventsToExclude(String eventsToExclude) {
		this.eventsToExclude = eventsToExclude;
	}

}
