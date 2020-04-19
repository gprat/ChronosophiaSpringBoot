package com.webapp.site;

import java.security.Principal;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.webapp.site.entities.Category;


@Controller
@RequestMapping("category")
public class CategoryController {
	
	@Inject CategoryService categoryService;
	@Inject UserService userService;
	
	@RequestMapping(value = {"list"}, method = RequestMethod.GET)
    public String list(Map<String, Object> model, Principal principal){
		model.put("categories", this.categoryService.getCategoriesByUsername(principal.getName()));
		return "category/list";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String createCategory(Map<String, Object> model){
		model.put("categoryForm",new CategoryForm());
		return("category/add");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String createCategory(@Valid CategoryForm categoryForm, BindingResult bindingResult, Map<String, Object> model, Principal principal){
		if(bindingResult.hasErrors()){
			model.put("categoryForm",new CategoryForm());
			return "category/add";
		}
		Category category = new Category();
		category.setName(categoryForm.getCategoryName());
		category.setUser(this.userService.findByUsername(principal.getName()));
		this.categoryService.save(category);
		return "redirect:list";
	}
	
	@RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
	public View deleteCategory(@PathVariable("id") long id, Principal principal){
		this.categoryService.delete(id, principal.getName());
		return new RedirectView("/category/list", true, false);
	}
	

}
