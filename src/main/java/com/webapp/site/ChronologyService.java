package com.webapp.site;

import java.util.List;

import com.webapp.site.entities.Chronology;

public interface ChronologyService {

	List<Chronology> getAllChronologies();

	Chronology getChronology(long id);

	void save(Chronology chronology);

	void delete(long id);
	
	ChronologyForm getChronologyForm(long id);
	
	List<Chronology> getChronologiesByUsername(String username);

}
