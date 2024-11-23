package com.webapp.site.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
@Table(name="country")
@NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idCountry;

	@NotBlank
	private String name;

	//bi-directional many-to-one association to City
	@OneToMany(mappedBy="country")
	private List<City> cities;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="country")
	private List<Event> events;

	//bi-directional many-to-one association to Figure
	@OneToMany(mappedBy="country")
	private List<Figure> figures;

	public Country() {
	}

	public Long getIdCountry() {
		return this.idCountry;
	}

	public void setIdCountry(Long idCountry) {
		this.idCountry = idCountry;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public List<City> getCities() {
		return this.cities;
	}

	@JsonIgnore
	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public City addCity(City city) {
		getCities().add(city);
		city.setCountry(this);

		return city;
	}

	public City removeCity(City city) {
		getCities().remove(city);
		city.setCountry(null);

		return city;
	}

	@JsonIgnore
	public List<Event> getEvents() {
		return this.events;
	}

	@JsonIgnore
	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setCountry(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setCountry(null);

		return event;
	}

	@JsonIgnore
	public List<Figure> getFigures() {
		return this.figures;
	}

	@JsonIgnore
	public void setFigures(List<Figure> figures) {
		this.figures = figures;
	}

	public Figure addFigure(Figure figure) {
		getFigures().add(figure);
		figure.setCountry(this);

		return figure;
	}

	public Figure removeFigure(Figure figure) {
		getFigures().remove(figure);
		figure.setCountry(null);

		return figure;
	}

}