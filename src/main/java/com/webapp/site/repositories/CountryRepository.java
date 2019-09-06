package com.webapp.site.repositories;

import org.springframework.data.repository.CrudRepository;

import com.webapp.site.entities.Country;

public interface CountryRepository extends CrudRepository<Country,Long>{
	
	Country getOneByName(String name);
}
