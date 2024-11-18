package com.webapp.site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.JsonNode;
import com.webapp.site.entities.City;
import com.webapp.site.entities.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.security.Principal;
import java.util.List;

@Validated
public interface CityService {

	@NotNull
	List<City> getAllCities ();
	
	City getCity(
			@Min(value = 1L, message = "{validate.cityService.getCity.id}")
            long id, String username
	);
	
	void save(@NotNull(message = "{validate.cityService.save.city}")
    		@Valid City city);
	
	void deleteCity(long id, String username);
	
	void setCountry(City city,String countryname);
	
	CityForm getCityForm(long id, String username);
	
	List<City> getCitiesByUsername(String username);
	
	List<City> getCitiesByEventYear(String username, int yearStart, int yearEnd);

	City GetCityByDetails(City city);

	City setCity(JsonNode cityNode, User user);
}
