package com.webapp.site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.webapp.site.entities.City;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.List;

@Validated
public interface CityService {

	@NotNull
	List<City> getAllCities ();
	
	City getCity(
			@Min(value = 1L, message = "{validate.cityService.getCity.id}")
            long id
	);
	
	void save(@NotNull(message = "{validate.cityService.save.city}")
    		@Valid City city);
	
	void deleteCity(long id);
	
	void setCountry(City city,String countryname);
	
	CityForm getCityForm(long id);
	
	List<City> getCitiesByUsername(String username);
	
	List<City> getCitiesByEventYear(String username, int yearStart, int yearEnd);
}
