package com.webapp.site.entities;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the painting database table.
 * 
 */
@Entity
@Table(name="painting")
@NamedQuery(name="Painting.findAll", query="SELECT p FROM Painting p")
public class Painting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idArt;

	//bi-directional one-to-one association to Art
	@OneToOne
	@JoinColumn(name="idArt")
	private Art art;

	//bi-directional many-to-one association to Event
	@ManyToOne
	@JoinColumn(name="idEvent")
	private Event event;

	//bi-directional many-to-one association to Figure
	@ManyToOne
	@JoinColumn(name="idCharacter")
	private Figure figure;

	public Painting() {
	}

	public Long getIdArt() {
		return this.idArt;
	}

	public void setIdArt(Long idArt) {
		this.idArt = idArt;
	}

	public Art getArt() {
		return this.art;
	}

	public void setArt(Art art) {
		this.art = art;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Figure getFigure() {
		return this.figure;
	}

	public void setFigure(Figure figure) {
		this.figure = figure;
	}

}