package com.webapp.site;

import java.util.List;

import com.webapp.site.entities.Date;

public interface DateService {

	List<Date> getAllDates();
	
	Date getDate(long id);
	
	void save(Date date);
	
	void delete(long id);
	
	Date getDate(Integer day, Integer month, Integer year);
	
	Date setDate(Integer day, Integer month, Integer year);
	
	boolean isValidDate(String date);
	
	
}
