package com.webapp.site;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webapp.site.entities.City;
import com.webapp.site.validation.NotBlank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.io.IOException;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("city")
public class CityController {
	
	@Inject CityService cityService;
	@Inject ObjectMapper objectMapper;
	@Inject UserService userService;
	
	@RequestMapping(value = {"list"}, method = RequestMethod.GET)
    public String list(Map<String, Object> model, Principal principal){
		model.put("cities", this.cityService.getCitiesByUsername(principal.getName()));
		model.put("eventsSpan", new EventsSpan());
		return "city/list";
	}
	
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String createCity(Map<String, Object> model)
    {
        model.put("cityForm", new CityForm());
        return "city/add";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public View createCity(CityForm form, Principal principal)
    {
    	City city;
    	if(form.getIdCity()==null||form.getIdCity()==0){
    		city = new City();
    	}
    	else{
    		city=this.cityService.getCity(form.getIdCity(), principal.getName());
    	}
    	if(city!=null) {
	    	city.setName(form.getCityname());
	    	cityService.setCountry(city, form.getCountryname());
	    	city.setLatitude(form.getLatitude().setScale(6, RoundingMode.HALF_UP));
	    	city.setLongitude(form.getLongitude().setScale(6, RoundingMode.HALF_UP));
	    	city.setUser(this.userService.findByUsername(principal.getName()));
	    	city.setDescription(form.getDescription());
	        cityService.save(city);
    	}
        return new RedirectView("/city/list", true, false);
    }
    
    @RequestMapping(value = "view", method = RequestMethod.POST)
    public String view(Map<String,Object> model, Principal principal, EventsSpan eventsSpan){
    	int startYear=-2000;
    	int endYear =  Calendar.getInstance().get(Calendar.YEAR);
    	if(eventsSpan.getStartYear()!=null) {
    		startYear=eventsSpan.getStartYear();
    	}
    	if(eventsSpan.getEndYear()!=null) {
    		endYear= eventsSpan.getEndYear();
    	}
    	
    	try {
    		if(eventsSpan.getStartYear()==null && eventsSpan.getEndYear()==null) {
    			model.put("cities", this.cityService.getCitiesByUsername(principal.getName()));
        		model.put("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
        	}
        	else {
        		model.put("cities", this.cityService.getCitiesByEventYear(principal.getName(), startYear,endYear));
        		model.put("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByEventYear(principal.getName(), startYear, endYear)));
        	}
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "city/view";
    }
    
    @RequestMapping(value = "/{id}", params ="update", method = RequestMethod.POST)
	public String ShowUpdateCityForm(@PathVariable("id") long id, Model model,Principal principal) {
		CityForm cityForm = this.cityService.getCityForm(id,principal.getName());
		model.addAttribute("cityForm", cityForm);
		return "city/add";
	}
    
    @RequestMapping(value = "/{id}", params ="delete", method = RequestMethod.POST)
	public View deleteCity(@PathVariable("id") long id, Principal principal){
		this.cityService.deleteCity(id,principal.getName());
		return new RedirectView("/city/list", true, false);
	}
    
    @RequestMapping(value = "/{id}", params ="view", method = RequestMethod.POST)
	public String view(@PathVariable("id") long id, Model model,Principal principal){
    	City city = cityService.getCity(id,principal.getName());
    	if(city!=null) {
			model.addAttribute("city", city);
			try{
				city.addEventsInJson = true;
				model.addAttribute("cityJSON", objectMapper.writeValueAsString(city));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
    	}
		return "city/viewcity";
	}

}
