package com.webapp.site.entities;


import java.io.Serializable;
import java.util.Date;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
 
@Entity
@Table(name="persistent_usernames")
public class PersistentLogin implements Serializable{
 
    @Id
    private String series;
 
    @Column(name="USERNAME", unique=true, nullable=false)
    private String username;
     
    @Column(name="TOKEN", unique=true, nullable=false)
    private String token;
     
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_used;
 
    public String getSeries() {
        return series;
    }
 
    public void setSeries(String series) {
        this.series = series;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getToken() {
        return token;
    }
 
    public void setToken(String token) {
        this.token = token;
    }
 
    public Date getLast_used() {
        return last_used;
    }
 
    public void setLast_used(Date last_used) {
        this.last_used = last_used;
    }
     
     
}