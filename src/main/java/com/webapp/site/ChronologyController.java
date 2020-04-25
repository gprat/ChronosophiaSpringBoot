package com.webapp.site;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
	@Inject AmazonClient amazonClient;
	
	@RequestMapping(value = {"list"}, method = RequestMethod.GET)
	public String list(Map<String, Object> model, Principal principal){
		model.put("chronologies", chronologyService.getChronologiesByUsername(principal.getName()));
		return "chronology/list";
	}
	
	@RequestMapping(value = "/id/{id}", params ="view", method = RequestMethod.POST)
	public String view(@PathVariable("id") long id, Model model, Principal principal){
		try {
			Chronology chronology = this.chronologyService.getChronology(id, principal.getName());
			if(chronology!=null) {
				model.addAttribute("chronologieJSON", objectMapper.writeValueAsString(chronology));
				model.addAttribute("titre",this.chronologyService.getChronology(id,principal.getName()).getName());
			}
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
			model.put("eventsJSON", objectMapper.writeValueAsString(this.eventService.getEventsByUsername(principal.getName())));
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
			model.put("eventsJSON", objectMapper.writeValueAsString(this.eventService.getEventsByUsername(principal.getName())));
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
			model.put("SelectedEventList", eventService.getEventsById(eventListToExclude,principal.getName()));
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
				model.put("SelectedEventList", eventService.getEventsById(eventListToExclude,principal.getName()));
			}
			model.put("AvailableEventList", eventService.getEventsFiltered(categoryList, figureList, cityList,eventListToExclude, principal.getName()));
			try{
				model.put("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
				model.put("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
				model.put("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
				model.put("eventsJSON", objectMapper.writeValueAsString(this.eventService.getEventsByUsername(principal.getName())));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return "chronology/chronologyForm";
		}
		Chronology chronology;
		if(chronologyForm.id!=null&&chronologyForm.id!=0){
			chronology=chronologyService.getChronology(chronologyForm.id,principal.getName());
			if(chronology==null) {
				chronology=new Chronology();
			}
		}
		else{
			chronology=new Chronology();
		}
		chronology.setName(chronologyForm.name);
		List<Event> eventList = new ArrayList<>();
		if(chronologyForm.eventList!=null&&!chronologyForm.eventList.isEmpty()){
			new ArrayList<String>(Arrays.asList(chronologyForm.eventList.split(","))).forEach(idEvent->eventList.add(eventService.getEvent(Long.parseLong(idEvent),principal.getName())));
			while (eventList.remove(null));
		}
		if(chronologyForm.events!=null&&!chronologyForm.events.isEmpty()){
			new ArrayList<String>(Arrays.asList(chronologyForm.events.split(","))).forEach(idEvent->eventList.add(eventService.getEvent(Long.parseLong(idEvent),principal.getName())));
			while (eventList.remove(null));
		}
		LinkedHashSet<Event> hashSet = new LinkedHashSet<Event>(eventList);
		List<Event> eventListNoDuplicates = new ArrayList<Event>(hashSet); 
		chronology.setEvents(eventListNoDuplicates);
		chronology.setUrl(chronologyForm.url);
		chronology.setDescription(chronologyForm.description);
		chronology.setUser(this.userService.findByUsername(principal.getName()));
		if(chronologyForm.getCategory()!=null&&!chronologyForm.getCategory().isEmpty()) {
			chronology.setCategory(categoryService.getCategory(Long.parseLong(chronologyForm.getCategory()), principal.getName()));
		}
		chronologyService.save(chronology);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/id/{id}", params ="update", method = RequestMethod.POST)
	public String ShowUpdateChronologyForm(@PathVariable("id") long id, Model model, Principal principal) {
		ChronologyForm chronologyForm = this.chronologyService.getChronologyForm(id,principal.getName() );
		model.addAttribute("chronologyForm", chronologyForm);
		model.addAttribute("selectEventForm",new SelectEventForm());
		List<Long> eventListToExclude = new ArrayList<>();
		if(chronologyForm.eventList!=null&&chronologyForm.eventList!=""){
			new ArrayList<String>(Arrays.asList(chronologyForm.eventList.split(","))).forEach(idEvent->eventListToExclude.add(Long.parseLong(idEvent)));
			model.addAttribute("SelectedEventList", eventService.getEventsById(eventListToExclude,principal.getName()));
		}
		model.addAttribute("AvailableEventList", eventService.getEventsFiltered(null, null, null, eventListToExclude, principal.getName()));
		try{
			model.addAttribute("figuresJSON", objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName())));
			model.addAttribute("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
			model.addAttribute("citiesJSON", objectMapper.writeValueAsString(this.cityService.getCitiesByUsername(principal.getName())));
			model.addAttribute("eventsJSON", objectMapper.writeValueAsString(this.eventService.getEventsByUsername(principal.getName())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "chronology/chronologyForm";
	}
	
	@RequestMapping(value = "/id/{id}", params ="delete", method = RequestMethod.POST)
	public View deleteChronology(@PathVariable("id") long id, Principal principal){
		this.chronologyService.delete(id, principal.getName());
		return new RedirectView("/chronology/list", true, false);
	}
	
	@RequestMapping(value = {"export"}, method = RequestMethod.GET)
	public String export(Map<String, Object> model, Principal principal){
		model.put("chronologies", chronologyService.getChronologiesByUsername(principal.getName()));
		return "chronology/export";
	}
	
	@PostMapping("/download")
	 public @ResponseBody void downloadFile(HttpServletRequest req ,HttpServletResponse resp, Principal principal) {
		String[] selectedIds = req.getParameterValues("selectedIds");
		String description = req.getParameter("description");
		List<Long> idList = new ArrayList();
		if(selectedIds!=null&&selectedIds.length>0) {
			new ArrayList<String>(Arrays.asList(selectedIds)).forEach(idChronology->idList.add(Long.parseLong(idChronology)));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyHHmmss");  
			LocalDateTime now = LocalDateTime.now();  
			String downloadFileName= "chronology_"+principal.getName()+"_"+dtf.format(now)+".json";
			String downloadStringContent = "";
			try {
				downloadStringContent = objectMapper.writeValueAsString(this.chronologyService.getChronologiesByUsernameAndIds(principal.getName(),idList));
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			amazonClient.uploadJSON(downloadFileName, downloadStringContent, description, principal.getName());
		}
	 }
	
	@GetMapping("/import")
	public String importView(Map<String, Object> model) {
		model.put("contents", this.amazonClient.listofJson("chronology"));
		return "chronology/import";
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
						Chronology chronology = new Chronology();
						chronology.setName(root.path("name").asText());
						JsonNode rootEventArray = root.path("eventsToImport");
						int eventCount = rootEventArray.size();
						if(!this.chronologyService.ExistChronology(chronology.getName(), eventCount, principal.getName())) {
							chronology.setDescription(root.path("descritpion").asText());
							chronology.setUrl(root.path("url").asText());
							List<Event> eventList = new ArrayList();
							for(JsonNode rootEvent : rootEventArray) { 
								Event event = this.eventService.importEvent(rootEvent, principal.getName());
								if(event!=null) {
									eventList.add(event);
								}
							}
							chronology.setEvents(eventList);
							JsonNode categoryNode = root.path("category");
							if(!categoryNode.isMissingNode()){
								chronology.setCategory(this.categoryService.setCategory(categoryNode.asText(),principal.getName()));
							}
							chronology.setUser(this.userService.findByUsername(principal.getName()));
							this.chronologyService.save(chronology);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
		return "redirect:list";
	}
}
