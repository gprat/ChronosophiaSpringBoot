package com.webapp.site;

import java.util.List;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.webapp.site.entities.City;
import com.webapp.site.entities.Country;
import com.webapp.site.entities.User;
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
	public City getCity(long id, String username) {
		City city = this.cityRepository.findById(id).get();
		if (!city.getUser().getUsername().equals(username)) {
			return null;
		}
		else return city;
	}

	@Override
	public void save(City city) {
		this.cityRepository.save(city);

	}

	@Override
	public void deleteCity(long id, String username) {
		City city = this.cityRepository.findById(id).get();
		if (city.getUser().getUsername().equals(username)) {
			this.cityRepository.deleteById(id);
		}
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
	public CityForm getCityForm(long id, String username){
		City city = getCity(id, username);
		if(city!=null) {
			CityForm form = new CityForm();
			form.setIdCity(id);
			form.setCityname(city.getName());
			form.setCountryname(city.getCountry().getName());
			form.setLatitude(city.getLatitude());
			form.setLongitude(city.getLongitude());
			form.setDescription(city.getDescription());
			return form;
		}
		else return new CityForm();
	}
	
	@Override
	public List<City> getCitiesByUsername(String username){
		return this.cityRepository.findByUser_username(username);
	}
	
	@Override
	public List<City> getCitiesByEventYear(String username, int yearStart, int yearEnd){
		return this.cityRepository.findByUser_usernameAndEvents_Date_YearBetween(username, yearStart, yearEnd);
	}
	
	@Override
	public City GetCityByDetails(City city) {
		City cityToReturn = null;
		List<City> cityList = this.cityRepository.findByNameAndCountry_nameAndUser_username(city.getName(), city.getCountry().getName(), city.getUser().getUsername());
		if(cityList!=null&&!cityList.isEmpty()) {
			for(City cityInList : cityList) {
				if(city.getLatitude().compareTo(cityInList.getLatitude().add(new BigDecimal(0.3)))<0
				&&city.getLatitude().compareTo(cityInList.getLatitude().subtract(new BigDecimal(0.3)))>0
				&&city.getLongitude().compareTo(cityInList.getLongitude().add(new BigDecimal(0.5)))<0
				&&city.getLongitude().compareTo(cityInList.getLongitude().subtract(new BigDecimal(0.5)))>0) {
					cityToReturn = cityInList;
				}
			}
		}
		return cityToReturn;
	}
	
	@Override
	public City setCity(JsonNode cityNode, User user) {
		City city = new City();
		city.setName(cityNode.path("name").asText());
		setCountry(city, cityNode.path("country").asText());
		city.setLatitude(new BigDecimal(cityNode.path("latitude").asDouble()));
		city.setLongitude(new BigDecimal(cityNode.path("longitude").asDouble()));
		city.setUser(user);
		city.setDescription(cityNode.path("description").asText());
		City cityTemp = GetCityByDetails(city);
		if(cityTemp!=null) {
			city=cityTemp;
		}
		else {
			save(city);
			GetCityByDetails(city);
		}
		return city;
	}

}
