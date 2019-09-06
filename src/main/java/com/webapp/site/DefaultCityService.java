package com.webapp.site;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.webapp.site.entities.City;
import com.webapp.site.entities.Country;
import com.webapp.site.repositories.CityRepository;
import com.webapp.site.repositories.CountryRepository;

@Service
public class DefaultCityService implements CityService {
	@Inject CityRepository cityRepository;
	@Inject CountryRepository countryRepository;
	
	@Override
	public List<City> getAllCities() {
		List<City> list = new ArrayList<>();
        this.cityRepository.findAll().forEach(e -> list.add(e));
        return list;
	}

	@Override
	public City getCity(long id) {
		return this.cityRepository.findById(id).get();
	}

	@Override
	public void save(City city) {
		this.cityRepository.save(city);

	}

	@Override
	public void deleteCity(long id) {
		this.cityRepository.deleteById(id);
	}
	
	@Override
	public void setCountry(City city,String countryname){
		Country country = countryRepository.getOneByName(countryname);
		if (country == null) {
			country = new Country();
			country.setName(countryname);
			countryRepository.save(country);
			country = countryRepository.getOneByName(countryname);
		}
		city.setCountry(country);
	}
	
	@Override
	public CityForm getCityForm(long id){
		City city = getCity(id);
		CityForm form = new CityForm();
		form.setIdCity(id);
		form.setCityname(city.getName());
		form.setCountryname(city.getCountry().getName());
		form.setLatitude(city.getLatitude());
		form.setLongitude(city.getLongitude());
		form.setDescription(city.getDescription());
		return form;
	}
	
	@Override
	public List<City> getCitiesByUsername(String username){
		return this.cityRepository.findByUser_username(username);
	}
	
	@Override
	public List<City> getCitiesByEventYear(String username, int yearStart, int yearEnd){
		return this.cityRepository.findByUser_usernameAndEvents_Date_YearBetween(username, yearStart, yearEnd);
	}

}
