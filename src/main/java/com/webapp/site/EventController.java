package com.webapp.site;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.site.entities.Category;
import com.webapp.site.entities.City;
import com.webapp.site.entities.Event;


import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("event")
public class EventController {

	@Inject EventService eventService;
	@Inject FigureService figureService;
	@Inject DateService dateService;
	@Inject UserService userService;
	@Inject CategoryService categoryService;
	@Inject CityService cityService;
	@Inject ObjectMapper objectMapper;
	@Inject AmazonClient amazonClient;
	
	@RequestMapping(value = {"list"}, method = RequestMethod.GET)
    public String list(Map<String, Object> model, Principal principal){
		model.put("selectEventForm",new SelectEventForm());
		model.put("events",this.eventService.getEventsByUsername(principal.getName()) );
		try{
			model.put("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
			model.put("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
			model.put("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "event/list";
	}
	
	@RequestMapping(value = "list",method = RequestMethod.POST)
	public String list(@ModelAttribute("selectEventForm") SelectEventForm selectEventForm,Map<String, Object> model, Principal principal ){
		model.put("selectEventForm",new SelectEventForm());
		List<Long> categoryList = new ArrayList<>();
		if(selectEventForm.categories!=null&&selectEventForm.categories!=""){
			new ArrayList<String>(Arrays.asList(selectEventForm.categories.split(","))).forEach(idCategory->categoryList.add(Long.parseLong(idCategory)));
		}
		model.put("events",this.eventService.getEventsByCategory(categoryList, principal.getName()));
		try{
			model.put("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
			model.put("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
			model.put("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "event/list";
	}
	
	@RequestMapping(value = "/id/{id}", params ="update", method = RequestMethod.POST)
	public String ShowUpdateEventForm(@PathVariable("id") long id, Model model, Principal principal) {
		EventForm eventForm = this.eventService.getEventForm(id, principal.getName());
		model.addAttribute("eventForm", eventForm);
		try{
			model.addAttribute("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
			model.addAttribute("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
			model.addAttribute("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "event/eventform";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String createEvent(Model model, Principal principal){
		EventForm eventForm = new EventForm();
		model.addAttribute("eventForm", eventForm);
			try {
				System.out.println("categoriesJSON : "+objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
				System.out.println("citiesJSON : "+objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
			model.addAttribute("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
			model.addAttribute("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			

		return "event/eventform";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String createEvent(@Valid EventForm eventForm, BindingResult bindingResult, Map<String, Object> model, Principal principal){
		if(bindingResult.hasErrors()){
			model.put("eventForm", eventForm);
			try{
				model.put("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
				model.put("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return "event/eventform";
		}
		Event event;
		if(eventForm.id!=null&&eventForm.id!=0){
			event=this.eventService.getEvent(eventForm.id,principal.getName());
			if(event==null) {
				event=new Event();
			}
		}
		else{
			event=new Event();
		}
		List<Category> categoryList = new ArrayList<>();
		event.setName(eventForm.name);
		event.setUrl(eventForm.getUrl());
		event.setDate(this.dateService.setDate(eventForm.day, eventForm.month, eventForm.year));
		event.setDescription(eventForm.description);
		event.setUser(this.userService.findByUsername(principal.getName()));
		if(eventForm.categories!=null&&eventForm.categories!=""){
			new ArrayList<String>(Arrays.asList(eventForm.categories.split(","))).forEach(idCategory->categoryList.add(categoryService.getCategory(Long.parseLong(idCategory), principal.getName())));
		}
		event.setCategories(categoryList);
		if(eventForm.idCity!=null&&eventForm.idCity!=""){
			City city = this.cityService.getCity(Long.parseLong(eventForm.idCity),principal.getName());
			if (city!=null) {
				event.setCity(city);
			}
		}
		this.eventService.save(event);
		
		return "redirect:list";
	}
	
	
	@RequestMapping(value = "/id/{id}", params ="delete", method = RequestMethod.POST)
	public View deleteEvent(@PathVariable("id") long id, Principal principal){
		this.eventService.delete(id, principal.getName());
		return new RedirectView("/event/list", true, false);
	}
	
	@RequestMapping(value = {"export"}, method = RequestMethod.GET)
    public String export(Map<String, Object> model, Principal principal){
		model.put("events",this.eventService.getEventsByUsername(principal.getName()) );
		return "event/export";
	}
	
	@PostMapping("/download")
	 public String downloadFile(HttpServletRequest req, Principal principal) {
		String[] selectedIds = req.getParameterValues("selectedIds");
		String description = req.getParameter("description");
		List<Long> idList = new ArrayList<Long>();
		if(selectedIds!=null&&selectedIds.length>0) {
			new ArrayList<String>(Arrays.asList(selectedIds)).forEach(idEvent->idList.add(Long.parseLong(idEvent)));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyHHmmss");  
			LocalDateTime now = LocalDateTime.now();  
			String downloadFileName= "event_"+principal.getName()+"_"+dtf.format(now)+".json";
			String downloadStringContent = "";
			try {
				downloadStringContent = objectMapper.writeValueAsString(this.eventService.getEventsByUsernameAndIds(principal.getName(),idList));
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			amazonClient.uploadJSON(downloadFileName, downloadStringContent, description, principal.getName());
		}
		return "redirect:import";
	 }
	
	@GetMapping("/import")
	public String importView(Map<String, Object> model) {
		model.put("contents", this.amazonClient.listofJson("event"));
		return "event/import";
	}
	 
	 @PostMapping("/upload")
	 public String importSelectedFiles(HttpServletRequest req, Principal principal) {
		 String[] selectedFilenames = req.getParameterValues("selectedFilenames");
		 for(String filename : selectedFilenames) {
			 InputStream input = amazonClient.getFile(filename);
			 if(input!=null) {
				 ObjectMapper mapper = new ObjectMapper();
				 try {
					JsonNode rootArray =  mapper.readTree(input);
					for (JsonNode root : rootArray) {
						this.eventService.importEvent(root, principal.getName());
					}
						
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		 }
		 return "redirect:list";
	 }
}
