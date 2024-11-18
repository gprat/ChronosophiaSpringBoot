package com.webapp.site.entities;

import java.io.Serializable;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the profile database table.
 * 
 */
@Entity
@Table(name="userprofile")
public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idProfile;

	private String name;

	public UserProfile() {
	}

	public Long getIdProfile() {
		return this.idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(mappedBy = "userProfiles")
	private Set<User> users = new HashSet<User>();

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idProfile == null) ? 0 : idProfile.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof UserProfile))
            return false;
        UserProfile other = (UserProfile) obj;
        if (idProfile == null) {
            if (other.idProfile != null)
                return false;
        } else if (!idProfile.equals(other.idProfile))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "UserProfile [id=" + idProfile + ", type=" + name + "]";
    }
}