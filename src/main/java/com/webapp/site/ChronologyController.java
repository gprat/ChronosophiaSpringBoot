package com.webapp.site;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.site.entities.Category;
import com.webapp.site.entities.Chronology;
import com.webapp.site.entities.Event;
import com.webapp.site.entities.Figure;


@Controller
@RequestMapping("chronology")
public class ChronologyController {

	@Inject ChronologyService chronologyService;
	@Inject FigureService figureService;
	@Inject CategoryService categoryService;
	@Inject CityService cityService;
	@Inject EventService eventService;
	@Inject UserService userService;
	@Inject ObjectMapper objectMapper;
	
	@RequestMapping(value = {"list"}, method = RequestMethod.GET)
	public String list(Map<String, Object> model, Principal principal){
		model.put("chronologies", chronologyService.getChronologiesByUsername(principal.getName()));
		return "chronology/list";
	}
	
	@RequestMapping(value = "/{id}", params ="view", method = RequestMethod.POST)
	public String view(@PathVariable("id") long id, Model model){
		try {
			model.addAttribute("chronologieJSON", objectMapper.writeValueAsString(this.chronologyService.getChronology(id)));
			model.addAttribute("titre",this.chronologyService.getChronology(id).getName());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "chronology/view";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String createChronology(Map<String, Object> model, Principal principal){
		ChronologyForm chronologyForm = new ChronologyForm();
		model.put("chronologyForm", chronologyForm);
		model.put("selectEventForm",new SelectEventForm());
		model.put("AvailableEventList", eventService.getEventsByUsername(principal.getName()));
		try{
			model.put("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
			model.put("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
			model.put("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "chronology/chronologyForm";
	}
	
	@RequestMapping(value="filter", method = RequestMethod.POST)
	public String FilterEvents(@ModelAttribute("selectEventForm") SelectEventForm selectEventForm,Map<String, Object> model, Principal principal) {
		ChronologyForm chronologyForm = new ChronologyForm();
		chronologyForm.eventList=selectEventForm.eventsToExclude;
		model.put("chronologyForm", chronologyForm);
		try{
			model.put("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
			model.put("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
			model.put("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		List<Long> categoryList = new ArrayList<>();
		if(selectEventForm.categories!=null&&selectEventForm.categories!=""){
			new ArrayList<String>(Arrays.asList(selectEventForm.categories.split(","))).forEach(idCategory->categoryList.add(Long.parseLong(idCategory)));
		}
		List<Long> figureList = new ArrayList<>();
		if(selectEventForm.figures!=null&&selectEventForm.figures!=""){
			new ArrayList<String>(Arrays.asList(selectEventForm.figures.split(","))).forEach(idFigure->figureList.add(Long.parseLong(idFigure)));
		}
		List<Long> cityList = new ArrayList<>();
		if(selectEventForm.cities!=null&&selectEventForm.cities!=""){
			new ArrayList<String>(Arrays.asList(selectEventForm.cities.split(","))).forEach(idCity->cityList.add(Long.parseLong(idCity)));
		}
		List<Long> eventListToExclude = new ArrayList<>();
		if(selectEventForm.eventsToExclude!=null&&selectEventForm.eventsToExclude!=""){
			new ArrayList<String>(Arrays.asList(selectEventForm.eventsToExclude.split(","))).forEach(idEvent->eventListToExclude.add(Long.parseLong(idEvent)));
			model.put("SelectedEventList", eventService.getEventsById(eventListToExclude));
		}
		model.put("AvailableEventList", eventService.getEventsFiltered(categoryList, figureList, cityList, eventListToExclude, principal.getName()));
		
		return "chronology/chronologyForm";
	}
	
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String createChronology(@Valid ChronologyForm chronologyForm, BindingResult bindingResult, Map<String, Object> model, Principal principal){
		if(bindingResult.hasErrors()){
			model.put("chronologyForm", chronologyForm);
			model.put("selectEventForm",new SelectEventForm());
			List<Long> cityList = new ArrayList<>();
			List<Long> categoryList = new ArrayList<>();
			List<Long> figureList = new ArrayList<>();
			List<Long> eventListToExclude = new ArrayList<>();
			if((chronologyForm.getEventList()!=null&&(chronologyForm.getEventList()!=""))){
				new ArrayList<String>(Arrays.asList(chronologyForm.getEventList().split(","))).forEach(idEvent->eventListToExclude.add(Long.parseLong(idEvent)));
				model.put("SelectedEventList", eventService.getEventsById(eventListToExclude));
			}
			model.put("AvailableEventList", eventService.getEventsFiltered(categoryList, figureList, cityList,eventListToExclude, principal.getName()));
			try{
				model.put("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
				model.put("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
				model.put("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return "chronology/chronologyForm";
		}
		Chronology chronology;
		if(chronologyForm.id!=null&&chronologyForm.id!=0){
			chronology=chronologyService.getChronology(chronologyForm.id);
		}
		else{
			chronology=new Chronology();
		}
		chronology.setName(chronologyForm.name);
		List<Event> eventList = new ArrayList<>();
		if(chronologyForm.eventList!=null){
			new ArrayList<String>(Arrays.asList(chronologyForm.eventList.split(","))).forEach(idEvent->eventList.add(eventService.getEvent(Long.parseLong(idEvent))));
		}
		chronology.setEvents(eventList);
		chronology.setUrl(chronologyForm.url);
		chronology.setDescription(chronologyForm.description);
		chronology.setUser(this.userService.findByUsername(principal.getName()));
		chronology.setCategory(categoryService.getCategory(Long.parseLong(chronologyForm.getCategory())));
		chronologyService.save(chronology);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/{id}", params ="update", method = RequestMethod.POST)
	public String ShowUpdateChronologyForm(@PathVariable("id") long id, Model model, Principal principal) {
		ChronologyForm chronologyForm = this.chronologyService.getChronologyForm(id);
		Chronology chronology = this.chronologyService.getChronology(id);
		model.addAttribute("chronologyForm", chronologyForm);
		model.addAttribute("selectEventForm",new SelectEventForm());
		List<Long> eventListToExclude = new ArrayList<>();
		if(chronologyForm.eventList!=null&&chronologyForm.eventList!=""){
			new ArrayList<String>(Arrays.asList(chronologyForm.eventList.split(","))).forEach(idEvent->eventListToExclude.add(Long.parseLong(idEvent)));
			model.addAttribute("SelectedEventList", eventService.getEventsById(eventListToExclude));
		}
		model.addAttribute("AvailableEventList", eventService.getEventsFiltered(null, null, null, eventListToExclude, principal.getName()));
		try{
			model.addAttribute("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
			model.addAttribute("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
			model.addAttribute("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "chronology/chronologyForm";
	}
	
	@RequestMapping(value = "/{id}", params ="delete", method = RequestMethod.POST)
	public View deleteChronology(@PathVariable("id") long id){
		this.chronologyService.delete(id);
		return new RedirectView("/chronology/list", true, false);
	}
}
