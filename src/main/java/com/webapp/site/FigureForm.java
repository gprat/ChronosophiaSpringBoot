package com.webapp.site;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FigureForm {
	
	Long id;

	@NotBlank
	String firstName;
	
	@NotBlank
	String lastName;
	
	@Max(31)
	Integer dayOfBirth;
	
	@Max(12)
	Integer monthOfBirth;
	
	@NotNull
	Integer yearOfBirth;
	
	@Max(31)
	Integer dayOfDeath;
	
	@Max(12)
	Integer monthOfDeath;
	
	Integer yearOfDeath;
	
	String categories;
	
	String roles;
	
	String url;
	
	@NotBlank
	String biography;
	
	String events;
	
	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(Integer dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public Integer getMonthOfBirth() {
		return monthOfBirth;
	}

	public void setMonthOfBirth(Integer monthOfBirth) {
		this.monthOfBirth = monthOfBirth;
	}

	public Integer getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(Integer yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public Integer getDayOfDeath() {
		return dayOfDeath;
	}

	public void setDayOfDeath(Integer dayOfDeath) {
		this.dayOfDeath = dayOfDeath;
	}

	public Integer getMonthOfDeath() {
		return monthOfDeath;
	}

	public void setMonthOfDeath(Integer monthOfDeath) {
		this.monthOfDeath = monthOfDeath;
	}

	public Integer getYearOfDeath() {
		return yearOfDeath;
	}

	public void setYearOfDeath(Integer yearOfDeath) {
		this.yearOfDeath = yearOfDeath;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	
	
}
