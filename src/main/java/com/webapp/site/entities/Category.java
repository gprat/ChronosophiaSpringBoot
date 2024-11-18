package com.webapp.site.entities;

import java.io.Serializable;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@JsonSerialize(using = ToStringSerializer.class)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCategory;

	private String name;

	//bi-directional many-to-one association to Almanac
	@OneToMany(mappedBy="category")
	private List<Almanac> almanacs;

	//bi-directional many-to-one association to Chronology
	@OneToMany(mappedBy="category")
	private List<Chronology> chronologies;

	//bi-directional many-to-many association to Event
	@ManyToMany(mappedBy="categories")
	private List<Event> events;

	//bi-directional many-to-many association to Figure
	@ManyToMany(mappedBy="categories")
	private List<Figure> figures;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;

	public Category() {
	}

	public Long getIdCategory() {
		return this.idCategory;
	}

	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public List<Almanac> getAlmanacs() {
		return this.almanacs;
	}

	public void setAlmanacs(List<Almanac> almanacs) {
		this.almanacs = almanacs;
	}

	public Almanac addAlmanac(Almanac almanac) {
		getAlmanacs().add(almanac);
		almanac.setCategory(this);

		return almanac;
	}

	public Almanac removeAlmanac(Almanac almanac) {
		getAlmanacs().remove(almanac);
		almanac.setCategory(null);

		return almanac;
	}

	@JsonIgnore
	public List<Chronology> getChronologies() {
		return this.chronologies;
	}

	public void setChronologies(List<Chronology> chronologies) {
		this.chronologies = chronologies;
	}

	public Chronology addChronology(Chronology chronology) {
		getChronologies().add(chronology);
		chronology.setCategory(this);

		return chronology;
	}

	public Chronology removeChronology(Chronology chronology) {
		getChronologies().remove(chronology);
		chronology.setCategory(null);

		return chronology;
	}
	
	@JsonIgnore
	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	@JsonIgnore
	public List<Figure> getFigures() {
		return this.figures;
	}

	public void setFigures(List<Figure> figures) {
		this.figures = figures;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}