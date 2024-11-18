package com.webapp.site.entities;

import java.io.Serializable;

import jakarta.persistence.*;


/**
 * The persistent class for the art database table.
 * 
 */
@Entity
@Table(name="art")
@NamedQuery(name="Art.findAll", query="SELECT a FROM Art a")
public class Art implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idArt;

	@Lob
	private String description;

	private String name;

	private String url;

	//bi-directional many-to-one association to Date
	@ManyToOne
	@JoinColumn(name="idDate")
	private Date date;

	//bi-directional many-to-one association to Figure
	@ManyToOne
	@JoinColumn(name="idArtist")
	private Figure figure;

	//bi-directional many-to-one association to Style
	@ManyToOne
	@JoinColumn(name="idStyle")
	private Style style;

	//bi-directional one-to-one association to Monument
	@OneToOne(mappedBy="art")
	private Monument monument;

	//bi-directional one-to-one association to Painting
	@OneToOne(mappedBy="art")
	private Painting painting;

	//bi-directional one-to-one association to Sculpture
	@OneToOne(mappedBy="art")
	private Sculpture sculpture;
	
	//bi-directional many-to-one association to User
	@ManyToOne
		@JoinColumn(name="idUser")
	private User user;

	public Art() {
	}

	public Long getIdArt() {
		return this.idArt;
	}

	public void setIdArt(Long idArt) {
		this.idArt = idArt;
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

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Figure getFigure() {
		return this.figure;
	}

	public void setFigure(Figure figure) {
		this.figure = figure;
	}

	public Style getStyle() {
		return this.style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public Monument getMonument() {
		return this.monument;
	}

	public void setMonument(Monument monument) {
		this.monument = monument;
	}

	public Painting getPainting() {
		return this.painting;
	}

	public void setPainting(Painting painting) {
		this.painting = painting;
	}

	public Sculpture getSculpture() {
		return this.sculpture;
	}

	public void setSculpture(Sculpture sculpture) {
		this.sculpture = sculpture;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}