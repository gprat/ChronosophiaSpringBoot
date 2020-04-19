package com.webapp.site;

import java.util.List;

import com.webapp.site.entities.Chronology;

public interface ChronologyService {

	List<Chronology> getAllChronologies();

	Chronology getChronology(long id, String username);

	void save(Chronology chronology);

	void delete(long id, String username);
	
	ChronologyForm getChronologyForm(long id, String username);
	
	List<Chronology> getChronologiesByUsername(String username);

}
