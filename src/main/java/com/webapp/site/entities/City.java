package com.webapp.site.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.webapp.site.validation.NotBlank;
import com.webapp.site.CitySerializer;

import java.util.List;


/**
 * The persistent class for the city database table.
 * 
 */
@Entity
@Table(name="city")
@JsonSerialize(using = CitySerializer.class)
@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCity;

	@NotBlank
	private String name;
	
	//bi-directional many-to-one association to Country
	@Valid
	@ManyToOne
	@JoinColumn(name="idCountry")
	private Country country;
	
	//private String completeName;
	
	private BigDecimal longitude;
	
	private BigDecimal latitude;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;
	
	@Lob
	private String description;
	
	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="city", fetch = FetchType.EAGER)
	private List<Event> events;

	//bi-directional many-to-one association to Monument
	@OneToMany(mappedBy="city")
	private List<Monument> monuments;
	
	
	@Transient
	public boolean addEventsInJson = false;

	public City() {
	}

	public Long getIdCity() {
		return this.idCity;
	}

	public void setIdCity(Long idCity) {
		this.idCity = idCity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		event.setCity(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setCity(null);

		return event;
	}

	@JsonIgnore
	public List<Monument> getMonuments() {
		return this.monuments;
	}

	@JsonIgnore
	public void setMonuments(List<Monument> monuments) {
		this.monuments = monuments;
	}

	public Monument addMonument(Monument monument) {
		getMonuments().add(monument);
		monument.setCity(this);

		return monument;
	}

	public Monument removeMonument(Monument monument) {
		getMonuments().remove(monument);
		monument.setCity(null);

		return monument;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String toString(){
		return name + ", " + country.getName();
	}

}