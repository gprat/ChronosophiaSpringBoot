package com.webapp.site.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUser;

	private String password;
	
	@Transient
    private String passwordConfirm;
	
    private String username;
    
    private String firstName;
 
    private String lastName;
 
    private String email;
	
	//bi-directional many-to-one association to Event
		@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Event> events;
		
	//bi-directional many-to-one association to Figure
		@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Figure> figures;
		
	//bi-directional many-to-one association to Art
		@OneToMany(mappedBy="user")
	private List<Art> arts;

	//bi-directional many-to-one association to Almanac
	@OneToMany(mappedBy="user")
	private List<Almanac> almanacs;

	//bi-directional many-to-one association to Chronology
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Chronology> chronologies;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<City> cities;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Category> categories;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Role> roles;

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_userprofile", 
             joinColumns = { @JoinColumn(name = "idUser") }, 
             inverseJoinColumns = { @JoinColumn(name = "idProfile") })
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>();

	public User() {
	}

	public Long getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}


	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

	public List<Almanac> getAlmanacs() {
		return this.almanacs;
	}

	public void setAlmanacs(List<Almanac> almanacs) {
		this.almanacs = almanacs;
	}

	public Almanac addAlmanac(Almanac almanac) {
		getAlmanacs().add(almanac);
		almanac.setUser(this);

		return almanac;
	}

	public Almanac removeAlmanac(Almanac almanac) {
		getAlmanacs().remove(almanac);
		almanac.setUser(null);

		return almanac;
	}

	public List<Chronology> getChronologies() {
		return this.chronologies;
	}

	public void setChronologies(List<Chronology> chronologies) {
		this.chronologies = chronologies;
	}

	public Chronology addChronology(Chronology chronology) {
		getChronologies().add(chronology);
		chronology.setUser(this);

		return chronology;
	}

	public Chronology removeChronology(Chronology chronology) {
		getChronologies().remove(chronology);
		chronology.setUser(null);

		return chronology;
	}


	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Figure> getFigures() {
		return figures;
	}

	public void setFigures(List<Figure> figures) {
		this.figures = figures;
	}

	public List<Art> getArts() {
		return arts;
	}

	public void setArts(List<Art> arts) {
		this.arts = arts;
	}
	
	public Art addArt(Art art) {
		getArts().add(art);
		art.setUser(this);

		return art;
	}

	public Art removeArt(Art art) {
		getArts().remove(art);
		art.setUser(null);

		return art;
	}
	
	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setUser(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setUser(null);

		return event;
	}
	
	public Figure addFigure(Figure figure) {
		getFigures().add(figure);
		figure.setUser(this);

		return figure;
	}

	public Figure removeFigure(Figure figure) {
		getFigures().remove(figure);
		figure.setUser(null);

		return figure;
	}
	
	public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }
 
    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }
    
    
 
    public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (idUser == null) {
            if (other.idUser != null)
                return false;
        } else if (!idUser.equals(other.idUser))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
}