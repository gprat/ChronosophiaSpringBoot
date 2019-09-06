package com.webapp.site;

import java.util.List;

import javax.validation.constraints.*;

public class EventForm {

	Long id;
	
	@NotNull
	String name;
	
	String idCity;
	
	String country;
	
	@Max(31)
	Integer day;
	
	@Max(12)
	Integer month;
	
	@NotNull
	Integer year;
	
	String categories;

	@NotNull
	String description;
	
	String figures;
	
	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFigures() {
		return figures;
	}

	public void setFigures(String figures) {
		this.figures = figures;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCity() {
		return idCity;
	}

	public void setIdCity(String idCity) {
		this.idCity = idCity;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}


	
	
}
