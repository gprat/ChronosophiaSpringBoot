package com.webapp.site.entities;

import java.io.Serializable;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.webapp.site.ChronologySerializer;

import java.util.List;


/**
 * The persistent class for the chronology database table.
 * 
 */
@Entity
@Table(name="chronology")
@JsonSerialize(using = ChronologySerializer.class)
@NamedQuery(name="Chronology.findAll", query="SELECT c FROM Chronology c")
public class Chronology implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idChronology;

	private String name;
	
	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="idCategory")
	private Category category;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idCreator")
	private User user;

	private String url;
	
	@Lob
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//bi-directional many-to-many association to Event
	@JoinTable(name = "chronology_event", joinColumns = {
	        @JoinColumn(name = "idChronology", referencedColumnName = "idChronology")}, inverseJoinColumns = {
	        @JoinColumn(name = "idEvent", referencedColumnName = "idEvent")})
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Event> events;

	public Chronology() {
	}

	public Long getIdChronology() {
		return this.idChronology;
	}

	public void setIdChronology(Long idChronology) {
		this.idChronology = idChronology;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@JsonIgnore
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}