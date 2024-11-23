package com.webapp.site.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.webapp.site.EventSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


/**
 * The persistent class for the event database table.
 * 
 */
@Entity
@Table(name="event")
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
@JsonSerialize(using = EventSerializer.class)
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @Column(name="idEvent")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idEvent;

	private String name;
	
	@Lob
	private String description;

	private String url;

	//bi-directional many-to-one association to City
	@ManyToOne
	@JoinColumn(name="idCity")
	private City city;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="idCountry")
	private Country country;

	//bi-directional many-to-one association to Date
	@ManyToOne
	@JoinColumn(name="idDate")
	private Date date;

	//bi-directional many-to-one association to Type
	@ManyToOne
	@JoinColumn(name="idType")
	private Type type;

	//bi-directional many-to-one association to User
	@ManyToOne
		@JoinColumn(name="idUser")
	private User user;
	
	//bi-directional many-to-one association to Painting
	@OneToMany(mappedBy="event")
	private List<Painting> paintings;

	//bi-directional many-to-many association to Almanac
	@ManyToMany(mappedBy = "events")
	private List<Almanac> almanacs;

	//bi-directional many-to-many association to Chronology
	@ManyToMany(mappedBy = "events")
	private List<Chronology> chronologies;

	//bi-directional many-to-many association to Category
	@JoinTable(name = "event_category", joinColumns = {
	        @JoinColumn(name = "idEvent", referencedColumnName = "idEvent")}, inverseJoinColumns = {
	        @JoinColumn(name = "idCategory", referencedColumnName = "idCategory")})
	@ManyToMany
	private List<Category> categories;

	
	
	//bi-directional many-to-many association to Figure
	
	@ManyToMany(mappedBy="events", fetch = FetchType.EAGER)
	private List<Figure> figures;

	public Event() {
	}

	public Long getIdEvent() {
		return this.idEvent;
	}

	public void setIdEvent(Long idEvent) {
		this.idEvent = idEvent;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@JsonIgnore
	public List<Painting> getPaintings() {
		return this.paintings;
	}

	public void setPaintings(List<Painting> paintings) {
		this.paintings = paintings;
	}

	public Painting addPainting(Painting painting) {
		getPaintings().add(painting);
		painting.setEvent(this);

		return painting;
	}

	public Painting removePainting(Painting painting) {
		getPaintings().remove(painting);
		painting.setEvent(null);

		return painting;
	}

	@JsonIgnore
	public List<Almanac> getAlmanacs() {
		return this.almanacs;
	}

	public void setAlmanacs(List<Almanac> almanacs) {
		this.almanacs = almanacs;
	}

	@JsonIgnore
	public List<Chronology> getChronologies() {
		return this.chronologies;
	}

	public void setChronologies(List<Chronology> chronologies) {
		this.chronologies = chronologies;
	}

	@JsonIgnore
	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Figure> getFigures() {
		return this.figures;
	}

	public void setFigures(List<Figure> figures) {
		this.figures = figures;
	}
	
	@JsonIgnore
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}