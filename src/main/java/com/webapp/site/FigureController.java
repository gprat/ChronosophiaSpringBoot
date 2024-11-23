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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.webapp.site.entities.Event;
import com.webapp.site.entities.Figure;
import com.webapp.site.entities.Role;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("figure")
public class FigureController {
	
	@Inject FigureService figureService;
	@Inject DateService dateService;
	@Inject UserService userService;
	@Inject RoleService roleService;
	@Inject CategoryService categoryService;
	@Inject EventService eventService;
	@Inject ObjectMapper objectMapper;
	@Inject AmazonClient amazonClient;
	
	private String getPrincipalUsername(Principal principal) {
		if (principal != null) {
			return principal.getName();
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() != null) {
			return auth.getName();
		}
		// Redirect to login will be handled by Spring Security configuration
		return null;
	}
	
	@RequestMapping(value = {"list"}, method = RequestMethod.GET)
    public String list(Map<String, Object> model, Principal principal){
		String username = getPrincipalUsername(principal);
		if (username == null) {
			return "redirect:/login";
		}
		
		model.put("selectFigureForm",new SelectFigureForm());
		model.put("figures",this.figureService.getFiguresByUsername(username));
		model.put("categoryList", this.categoryService.getCategoriesByUsername(username));
		model.put("roleList", this.roleService.getRolesByUsername(username));
		return "figure/list";
	}
	
	@RequestMapping(value = "list",method = RequestMethod.POST)
	public String list(@ModelAttribute("selectFigureForm") SelectFigureForm selectFigureForm,Map<String, Object> model, Principal principal ){
		String username = getPrincipalUsername(principal);
		if (username == null) {
			return "redirect:/login";
		}
		
		model.put("selectFigureForm",new SelectFigureForm());
		model.put("figures",this.figureService.getFiguresByCategoryAndRole(selectFigureForm.category, selectFigureForm.role, username));
		model.put("categoryList", this.categoryService.getCategoriesByUsername(username));
		model.put("roleList", this.roleService.getRolesByUsername(username));
		return "figure/list";
	}
	
	@RequestMapping(value = "/id/{id}", params ="update", method = RequestMethod.POST)
	public String showUpdateFigureForm(@PathVariable("id") long id, Model model, Principal principal) {
		FigureForm figureForm = this.figureService.getFigureForm(id, principal.getName());
		try{
			model.addAttribute("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
			model.addAttribute("rolesJSON", objectMapper.writeValueAsString(this.roleService.getRolesByUsername(principal.getName())));
			model.addAttribute("eventsJSON", objectMapper.writeValueAsString(this.eventService.getEventsByUsername(principal.getName())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		model.addAttribute("figureForm", figureForm);
		return "figure/figureform";

	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String createFigure(Map<String, Object> model, Principal principal){
		 model.put("figureForm", new FigureForm());
		 try{
				model.put("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
				model.put("rolesJSON", objectMapper.writeValueAsString(this.roleService.getRolesByUsername(principal.getName())));
				model.put("eventsJSON", objectMapper.writeValueAsString(this.eventService.getEventsByUsername(principal.getName())));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		 return "figure/figureform";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String createFigure(@Valid FigureForm form, BindingResult bindingResult, Map<String, Object> model, Principal principal){
		if(bindingResult.hasErrors()) {
			model.put("figureForm",form);
			try{
				model.put("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
				model.put("rolesJSON", objectMapper.writeValueAsString(this.roleService.getRolesByUsername(principal.getName())));
				model.put("eventsJSON", objectMapper.writeValueAsString(this.eventService.getEventsByUsername(principal.getName())));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return "figure/figureform";
		}
		
		Figure figure;
		if(form.id!=null && form.id!=0){
			figure = this.figureService.getFigure(form.id, principal.getName());
			if(figure==null) {
				figure = new Figure();
			}
		}else{
			figure = new Figure();
		} 
		List<Category> categoryList = new ArrayList<>();
		List<Role>	roleList = new ArrayList<>();
		List<Event>	eventList = new ArrayList<>();
		figure.setFirstName(form.firstName);
		figure.setLastName(form.lastName);
		figure.setBirthDate(this.dateService.setDate(form.dayOfBirth, form.monthOfBirth, form.yearOfBirth));
		figure.setDeathDate(this.dateService.setDate(form.dayOfDeath, form.monthOfDeath, form.yearOfDeath));
		figure.setUser(this.userService.findByUsername(principal.getName()));
		if(form.categories!=null){
			new ArrayList<String>(Arrays.asList(form.categories.split(","))).forEach(idCategory->categoryList.add(categoryService.getCategory(Long.parseLong(idCategory), principal.getName())));
		}
		if(form.events!=null){
			new ArrayList<String>(Arrays.asList(form.events.split(","))).forEach(idEvent->eventList.add(eventService.getEvent(Long.parseLong(idEvent), principal.getName())));
		}
		if(form.roles!=null){
			new ArrayList<String>(Arrays.asList(form.roles.split(","))).forEach(idRole->roleList.add(roleService.getRole(Long.parseLong(idRole),principal.getName())));
		}
		figure.setCategories(categoryList);
		figure.setRoles(roleList);
		figure.setBiography(form.biography);
		figure.setEvents(eventList);
		figure.setUrl(form.url);
		this.figureService.save(figure);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/id/{id}", params ="delete", method = RequestMethod.POST)
	public View deleteFigure(@PathVariable("id") long id, Principal principal){
		this.figureService.delete(id, principal.getName());
		return new RedirectView("/figure/list", true, false);
	}
	
	@RequestMapping(value = "/id/{id}", params ="view", method = RequestMethod.POST)
	public String view(@PathVariable("id") long id, Model model, Principal principal){
		model.addAttribute("figure", figureService.getFigure(id, principal.getName()));
		try{
			model.addAttribute("figureJSON", objectMapper.writeValueAsString(this.figureService.getFigure(id, principal.getName())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "figure/view";
	}
	
	@PostMapping("/download")
	 public String downloadFile(HttpServletRequest req, Principal principal) {
		String[] selectedIds = req.getParameterValues("selectedIds");
		List<Long> idList = new ArrayList<Long>();
		String description = req.getParameter("description");
		if(selectedIds!=null&&selectedIds.length>0) {
			new ArrayList<String>(Arrays.asList(selectedIds)).forEach(idFigure->idList.add(Long.parseLong(idFigure)));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyHHmmss");  
			LocalDateTime now = LocalDateTime.now();  
			String downloadFileName= "figure_"+principal.getName()+"_"+dtf.format(now)+".json";
			String downloadStringContent = "";
			try {
				downloadStringContent = objectMapper.writeValueAsString(this.figureService.getFiguresByUsernameAndIds(principal.getName(),idList));
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			amazonClient.uploadJSON(downloadFileName, downloadStringContent, description, principal.getName());
		}
		return "redirect:import";
	 }
	
	@GetMapping("/import")
	public String importView(Map<String, Object> model) {
		model.put("contents", this.amazonClient.listofJson("figure"));
		return "figure/import";
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
						Figure figure = new Figure();
						figure.setFirstName(root.path("firstName").asText());
						figure.setLastName(root.path("lastName").asText());
						figure.setUser(this.userService.findByUsername(principal.getName()));
						JsonNode birthDateNode = root.path("birthDate");
						if (!birthDateNode.isMissingNode()) figure.setBirthDate(this.dateService.setDate(birthDateNode.path("day").asInt(), birthDateNode.path("month").asInt(), birthDateNode.path("year").asInt()));
						JsonNode deathDateNode = root.path("deathDate");
						if (!deathDateNode.isMissingNode()) figure.setDeathDate(this.dateService.setDate(deathDateNode.path("day").asInt(), deathDateNode.path("month").asInt(), deathDateNode.path("year").asInt()));
						if(!this.figureService.existsFigure(figure.getFirstName(), figure.getLastName(), figure.getBirthDate().getYear(), principal.getName())) {
							List<Event> eventList = new ArrayList<Event>();
							for(JsonNode rootEvent : root.path("eventsToImport")) { 
								Event event = this.eventService.importEvent(rootEvent, principal.getName());
								if(event!=null) {
									eventList.add(event);
								}
							}
							figure.setEvents(eventList);
							List<Category> categoryList =  new ArrayList<>();
							for(JsonNode categoryNode : root.path("categories")) {
								String categoryName = categoryNode.path("name").asText();
								categoryList.add(this.categoryService.setCategory(categoryName, principal.getName()));
							}
							figure.setCategories(categoryList);
							List<Role> roleList =  new ArrayList<>();
							for(JsonNode roleNode : root.path("roles")) {
								String roleName = roleNode.path("name").asText();
								 Role role = this.roleService.getRoleByNameAndUsername(roleName, principal.getName());
								 if (role == null) {
									 role = new Role();
									 role.setName(roleName);
									 role.setUser(figure.getUser());
									 this.roleService.save(role);
									 role = this.roleService.getRoleByNameAndUsername(roleName, principal.getName());
								 }
								 roleList.add(role);
							}
							figure.setRoles(roleList);
							figure.setBiography(root.path("biography").asText());
							figure.setUrl(root.path("url").asText());
							this.figureService.save(figure);
						}
						
					}
						
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		 }
		 return "redirect:list";
	 }
	
	@RequestMapping(value = {"export"}, method = RequestMethod.GET)
    public String export(Map<String, Object> model, Principal principal){
		model.put("figures",this.figureService.getFiguresByUsername(principal.getName()));
		return "figure/export";
	}
}
