package com.webapp.site.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the monument database table.
 * 
 */
@Entity
@Table(name="monument")
@NamedQuery(name="Monument.findAll", query="SELECT m FROM Monument m")
public class Monument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idArt;

	//bi-directional one-to-one association to Art
	@OneToOne
	@JoinColumn(name="idArt")
	private Art art;

	//bi-directional many-to-one association to City
	@ManyToOne
	@JoinColumn(name="idCity")
	private City city;

	public Monument() {
	}

	public int getIdArt() {
		return this.idArt;
	}

	public void setIdArt(int idArt) {
		this.idArt = idArt;
	}

	public Art getArt() {
		return this.art;
	}

	public void setArt(Art art) {
		this.art = art;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}