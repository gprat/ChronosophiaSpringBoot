package com.webapp.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
import com.webapp.site.entities.Date;
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
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
	
	
	@RequestMapping(value = {"list"}, method = RequestMethod.GET)
    public String list(Map<String, Object> model, Principal principal){
		model.put("selectFigureForm",new SelectFigureForm());
		model.put("figures",this.figureService.getFiguresByUsername(principal.getName()));
		model.put("categoryList", this.categoryService.getCategoriesByUsername(principal.getName()));
		model.put("roleList", this.roleService.getRolesByUsername(principal.getName()));
		return "figure/list";
	}
	
	@RequestMapping(value = "list",method = RequestMethod.POST)
	public String list(@ModelAttribute("selectFigureForm") SelectFigureForm selectFigureForm,Map<String, Object> model, Principal principal ){
		model.put("selectFigureForm",new SelectFigureForm());
		model.put("figures",this.figureService.getFiguresByCategoryAndRole(selectFigureForm.category, selectFigureForm.role, principal.getName()));
		model.put("categoryList", this.categoryService.getCategoriesByUsername(principal.getName()));
		model.put("roleList", this.roleService.getRolesByUsername(principal.getName()));
		return "figure/list";
	}
	
	@RequestMapping(value = "/{id}", params ="update", method = RequestMethod.POST)
	public String showUpdateFigureForm(@PathVariable("id") long id, Model model, Principal principal) {
		FigureForm figureForm = this.figureService.getFigureForm(id, principal.getName());
		try{
			model.addAttribute("categoriesJSON", objectMapper.writeValueAsString(this.categoryService.getCategoriesByUsername(principal.getName())));
			model.addAttribute("rolesJSON", objectMapper.writeValueAsString(this.roleService.getRolesByUsername(principal.getName())));
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
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		 return "figure/figureform";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public View createFigure(FigureForm form, Principal principal){
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
		figure.setFirstName(form.firstName);
		figure.setLastName(form.lastName);
		figure.setBirthDate(this.dateService.setDate(form.dayOfBirth, form.monthOfBirth, form.yearOfBirth));
		figure.setDeathDate(this.dateService.setDate(form.dayOfDeath, form.monthOfDeath, form.yearOfDeath));
		figure.setUser(this.userService.findByUsername(principal.getName()));
		if(form.categories!=null){
			new ArrayList<String>(Arrays.asList(form.categories.split(","))).forEach(idCategory->categoryList.add(categoryService.getCategory(Long.parseLong(idCategory))));
		}
		if(form.roles!=null){
			new ArrayList<String>(Arrays.asList(form.roles.split(","))).forEach(idRole->roleList.add(roleService.getRole(Long.parseLong(idRole))));
		}
		figure.setCategories(categoryList);
		figure.setRoles(roleList);
		figure.setBiography(form.biography);
		figure.setUrl(form.url);
		this.figureService.save(figure);
		return new RedirectView("/figure/list", true, false);
	}
	
	@RequestMapping(value = "/{id}", params ="delete", method = RequestMethod.POST)
	public View deleteFigure(@PathVariable("id") long id, Principal principal){
		this.figureService.delete(id, principal.getName());
		return new RedirectView("/figure/list", true, false);
	}
	
	@RequestMapping(value = "/{id}", params ="view", method = RequestMethod.POST)
	public String view(@PathVariable("id") long id, Model model, Principal principal){
		model.addAttribute("figure", figureService.getFigure(id, principal.getName()));
		try{
			model.addAttribute("figureJSON", objectMapper.writeValueAsString(this.figureService.getFigure(id, principal.getName())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "figure/view";
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	 public @ResponseBody 
	 void downloadFile(HttpServletResponse resp, Principal principal) {
	  String downloadFileName= "download.json";
	  String downloadStringContent = "";
		try {
			downloadStringContent = objectMapper.writeValueAsString(this.figureService.getFiguresByUsername(principal.getName()));
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
				Figure figure = new Figure();
				figure.setFirstName(root.findPath("firstName").asText());
				figure.setLastName(root.findPath("lastName").asText());
				figure.setUser(this.userService.findByUsername(principal.getName()));
				JsonNode birthDateNode = root.path("birthDate");
				if (!birthDateNode.isMissingNode()) figure.setBirthDate(this.dateService.setDate(birthDateNode.path("day").asInt(), birthDateNode.path("month").asInt(), birthDateNode.path("year").asInt()));
				JsonNode deathDateNode = root.path("deathDate");
				if (!deathDateNode.isMissingNode()) figure.setDeathDate(this.dateService.setDate(deathDateNode.path("day").asInt(), deathDateNode.path("month").asInt(), deathDateNode.path("year").asInt()));
				if(!this.figureService.existsFigure(figure.getFirstName(), figure.getLastName(), figure.getBirthDate().getYear(), principal.getName())) {
					List<Event> eventList = new ArrayList();
					for(JsonNode rootEvent : root.findPath("eventsToImport")) { 
						Event event = this.eventService.importEvent(rootEvent, principal.getName());
						if(event!=null) {
							eventList.add(event);
						}
					}
					figure.setEvents(eventList);
					List<Category> categoryList =  new ArrayList<>();
					for(JsonNode categoryNode : root.path("categories")) {
						String categoryName = categoryNode.path("name").asText();
						 Category category = this.categoryService.getCategoryByNameAndUsername(categoryName, principal.getName());
						 if (category == null) {
							 category = new Category();
							 category.setName(categoryName);
							 category.setUser(figure.getUser());
							 this.categoryService.save(category);
							 category = this.categoryService.getCategoryByNameAndUsername(categoryName, principal.getName());
						 }
						 categoryList.add(category);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "redirect:list";
	 }
}
