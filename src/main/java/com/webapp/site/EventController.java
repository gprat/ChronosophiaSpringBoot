package com.webapp.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.site.entities.Category;
import com.webapp.site.entities.City;
import com.webapp.site.entities.Event;
import com.webapp.site.entities.Figure;
import com.webapp.site.entities.Role;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
	
	@RequestMapping(value = "/{id}", params ="update", method = RequestMethod.POST)
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
	public String createEvent(Map<String, Object> model, Principal principal){
		EventForm eventForm = new EventForm();
		model.put("eventForm", eventForm);
		try{
			model.put("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
			model.put("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getAllCategories()));
			model.put("citiesJSON", objectMapper.writeValueAsString(this.cityService.getAllCities()));
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
				model.put("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
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
		List<Figure> figureList = new ArrayList<>();
		event.setName(eventForm.name);
		event.setUrl(eventForm.getUrl());
		event.setDate(this.dateService.setDate(eventForm.day, eventForm.month, eventForm.year));
		event.setDescription(eventForm.description);
		event.setUser(this.userService.findByUsername(principal.getName()));
		if(eventForm.categories!=null&&eventForm.categories!=""){
			new ArrayList<String>(Arrays.asList(eventForm.categories.split(","))).forEach(idCategory->categoryList.add(categoryService.getCategory(Long.parseLong(idCategory))));
		}
		if(eventForm.figures!=null&&eventForm.figures!="") {
			new ArrayList<String>(Arrays.asList(eventForm.figures.split(","))).forEach(idFigure->figureList.add(figureService.getFigure(Long.parseLong(idFigure),principal.getName())));
			while(figureList.remove(null));
		}
		event.setCategories(categoryList);
		event.setFigures(figureList);
		if(eventForm.idCity!=null&&eventForm.idCity!=""){
			City city = this.cityService.getCity(Long.parseLong(eventForm.idCity),principal.getName());
			if (city!=null) {
				event.setCity(city);
			}
		}
		this.eventService.save(event);
		
		return "redirect:list";
	}
	
	
	@RequestMapping(value = "/{id}", params ="delete", method = RequestMethod.POST)
	public View deleteEvent(@PathVariable("id") long id, Principal principal){
		this.eventService.delete(id, principal.getName());
		return new RedirectView("/event/list", true, false);
	}
	
	 @RequestMapping(value = "/download", method = RequestMethod.GET)
	 public @ResponseBody 
	 void downloadFile(HttpServletResponse resp, Principal principal) {
	  String downloadFileName= "download.json";
	  String downloadStringContent = "";
		try {
			downloadStringContent = objectMapper.writeValueAsString(this.eventService.getEventsByUsername(principal.getName()));
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  try {
	   OutputStream out = resp.getOutputStream();
	   resp.setContentType("text/plain; charset=utf-8");
	   resp.addHeader("Content-Disposition","attachment; filename=\"" + downloadFileName + "\"");
	   out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
	   out.flush();
	   out.close();

	  } catch (IOException e) {
	  }
	 }
	 
	 @PostMapping("/upload")
	 public String singleFileUpload(@RequestParam("file") MultipartFile file, Principal principal) {
		 ObjectMapper mapper = new ObjectMapper();
		 try {
			JsonNode rootArray =  mapper.readTree(file.getInputStream());
			for (JsonNode root : rootArray) {
				this.eventService.importEvent(root, principal.getName());
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "redirect:list";
	 }
}
