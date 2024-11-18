package com.webapp.site.entities;

import java.io.Serializable;

import jakarta.persistence.*;

import java.util.List;


/**
 * The persistent class for the almanac database table.
 * 
 */
@Entity
@Table(name="almanac")
@NamedQuery(name="Almanac.findAll", query="SELECT a FROM Almanac a")
public class Almanac implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idAlmanac;

	private String name;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="idCategory")
	private Category category;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idCreator")
	private User user;

	//bi-directional many-to-many association to Event
	@JoinTable(name = "almanac_event", joinColumns = {
	        @JoinColumn(name = "idAlmanac", referencedColumnName = "idAlmanac")}, inverseJoinColumns = {
	        @JoinColumn(name = "idEvent", referencedColumnName = "idEvent")})
	@ManyToMany
	private List<Event> events;

	public Almanac() {
	}

	public Long getIdAlmanac() {
		return this.idAlmanac;
	}

	public void setIdAlmanac(Long idAlmanac) {
		this.idAlmanac = idAlmanac;
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

}