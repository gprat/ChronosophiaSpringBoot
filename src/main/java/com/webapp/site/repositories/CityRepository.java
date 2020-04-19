package com.webapp.site.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.City;

public interface CityRepository extends CrudRepository<City,Long> {

	List<City> findByUser_username(String username);
	
	List<City> findByUser_usernameAndEvents_Date_YearBetween(String username, int yearStart, int yearEnd);
	
	List<City> findByNameAndCountry_nameAndUser_username(String name, String countryName, String username);
}
