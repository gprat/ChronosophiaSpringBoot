package com.webapp.site.repositories;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.Date;

public interface DateRepository extends CrudRepository<Date,Long> {

	Date getOneByDayAndMonthAndYear(int day, int month, int year);
	
	Date getOneByMonthAndYearAndDayIsNull(int month, int year);
	
	Date getOneByYearAndMonthIsNull(int year);
}
