package com.webapp.site.entities;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the sculpture database table.
 * 
 */
@Entity
@Table(name="sculpture")
@NamedQuery(name="Sculpture.findAll", query="SELECT s FROM Sculpture s")
public class Sculpture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idArt;

	//bi-directional many-to-one association to Figure
	@ManyToOne
	@JoinColumn(name="idCharacter")
	private Figure figure;

	//bi-directional one-to-one association to Art
	@OneToOne
	@JoinColumn(name="idArt")
	private Art art;

	public Sculpture() {
	}

	public Long getIdArt() {
		return this.idArt;
	}

	public void setIdArt(Long idArt) {
		this.idArt = idArt;
	}

	public Figure getFigure() {
		return this.figure;
	}

	public void setFigure(Figure figure) {
		this.figure = figure;
	}

	public Art getArt() {
		return this.art;
	}

	public void setArt(Art art) {
		this.art = art;
	}

}