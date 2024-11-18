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


/**
 * The persistent class for the date database table.
 * 
 */
@Entity
@Table(name="date")
@NamedQuery(name="Date.findAll", query="SELECT d FROM Date d")
public class Date implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idDate;

	private Integer year;
	
	private Integer month;
	
	private Integer day;

	//bi-directional many-to-one association to Art
	@OneToMany(mappedBy="date")
	private List<Art> arts;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="date")
	private List<Event> events;

	//bi-directional many-to-one association to Figure
	@OneToMany(mappedBy="birthDate")
	private List<Figure> figures1;

	//bi-directional many-to-one association to Figure
	@OneToMany(mappedBy="deathDate")
	private List<Figure> figures2;

	//bi-directional many-to-one association to Style
	@OneToMany(mappedBy="date1")
	private List<Style> styles1;

	//bi-directional many-to-one association to Style
	@OneToMany(mappedBy="date2")
	private List<Style> styles2;

	public Date() {
	}

	public Date(Integer day, Integer month, Integer year) {
		super();
		if (month!=null&&month!=0){
			this.month = month;
			if (day!=null&&day!=0) {this.day = day;}
		}
		this.year = year;
	}

	public Long getIdDate() {
		return this.idDate;
	}

	public void setIdDate(Long idDate) {
		this.idDate = idDate;
	}

	public Integer getDay() {
		return this.day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@JsonIgnore
	public List<Art> getArts() {
		return this.arts;
	}

	public void setArts(List<Art> arts) {
		this.arts = arts;
	}

	public Art addArt(Art art) {
		getArts().add(art);
		art.setDate(this);

		return art;
	}

	public Art removeArt(Art art) {
		getArts().remove(art);
		art.setDate(null);

		return art;
	}

	@JsonIgnore
	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setDate(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setDate(null);

		return event;
	}

	@JsonIgnore
	public List<Figure> getFigures1() {
		return this.figures1;
	}

	public void setFigures1(List<Figure> figures1) {
		this.figures1 = figures1;
	}

	public Figure addFigures1(Figure figures1) {
		getFigures1().add(figures1);
		figures1.setBirthDate(this);

		return figures1;
	}

	public Figure removeFigures1(Figure figures1) {
		getFigures1().remove(figures1);
		figures1.setBirthDate(null);

		return figures1;
	}

	@JsonIgnore
	public List<Figure> getFigures2() {
		return this.figures2;
	}

	public void setFigures2(List<Figure> figures2) {
		this.figures2 = figures2;
	}

	public Figure addFigures2(Figure figures2) {
		getFigures2().add(figures2);
		figures2.setDeathDate(this);

		return figures2;
	}

	public Figure removeFigures2(Figure figures2) {
		getFigures2().remove(figures2);
		figures2.setDeathDate(null);

		return figures2;
	}

	@JsonIgnore
	public List<Style> getStyles1() {
		return this.styles1;
	}

	public void setStyles1(List<Style> styles1) {
		this.styles1 = styles1;
	}

	public Style addStyles1(Style styles1) {
		getStyles1().add(styles1);
		styles1.setDate1(this);

		return styles1;
	}

	public Style removeStyles1(Style styles1) {
		getStyles1().remove(styles1);
		styles1.setDate1(null);

		return styles1;
	}

	@JsonIgnore
	public List<Style> getStyles2() {
		return this.styles2;
	}

	public void setStyles2(List<Style> styles2) {
		this.styles2 = styles2;
	}

	public Style addStyles2(Style styles2) {
		getStyles2().add(styles2);
		styles2.setDate2(this);

		return styles2;
	}

	public Style removeStyles2(Style styles2) {
		getStyles2().remove(styles2);
		styles2.setDate2(null);

		return styles2;
	}
	
	public String toString(){
		String sDate = "";
		if (this.month != null) {
			if (this.day != null) {
				sDate = String.valueOf(this.day)+" ";
			}
			switch (this.month) {
			case 1:
				sDate += "janvier";
				break;
			case 2:
				sDate += "février";
				break;
			case 3:
				sDate += "mars";
				break;
			case 4:
				sDate += "avril";
				break;
			case 5:
				sDate += "mai";
				break;
			case 6:
				sDate += "juin";
				break;
			case 7:
				sDate += "juillet";
				break;
			case 8:
				sDate += "août";
				break;
			case 9:
				sDate += "septembre";
				break;
			case 10:
				sDate += "octobre";
				break;
			case 11:
				sDate += "novembre";
				break;
			case 12:
				sDate += "décembre";
				break;
			default:
			}
			sDate+=" ";
		}
		sDate += String.valueOf(this.year);
		return sDate;
	}
	
	public String toSerializer(){
		String result = "";
		result += String.valueOf(this.year);
		result +="-";
		result += String.valueOf(this.month);
		result +="-";
		result += String.valueOf(this.day);
		result += " 12:00:00";
		return result;
	}

}