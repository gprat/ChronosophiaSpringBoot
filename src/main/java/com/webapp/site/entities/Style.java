package com.webapp.site.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the style database table.
 * 
 */
@Entity
@Table(name="style")
@NamedQuery(name="Style.findAll", query="SELECT s FROM Style s")
public class Style implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idStyle;

	@Lob
	private String description;

	private String name;

	//bi-directional many-to-one association to Art
	@OneToMany(mappedBy="style")
	private List<Art> arts;

	//bi-directional many-to-one association to Date
	@ManyToOne
	@JoinColumn(name="idBeginDate")
	private Date date1;

	//bi-directional many-to-one association to Date
	@ManyToOne
	@JoinColumn(name="idEndDate")
	private Date date2;

	public Style() {
	}

	public Long getIdStyle() {
		return this.idStyle;
	}

	public void setIdStyle(Long idStyle) {
		this.idStyle = idStyle;
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

	public List<Art> getArts() {
		return this.arts;
	}

	public void setArts(List<Art> arts) {
		this.arts = arts;
	}

	public Art addArt(Art art) {
		getArts().add(art);
		art.setStyle(this);

		return art;
	}

	public Art removeArt(Art art) {
		getArts().remove(art);
		art.setStyle(null);

		return art;
	}

	public Date getDate1() {
		return this.date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return this.date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

}