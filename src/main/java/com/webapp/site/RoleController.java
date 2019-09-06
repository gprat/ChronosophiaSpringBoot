package com.webapp.site;

import java.security.Principal;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.webapp.site.entities.Role;

@Controller
@RequestMapping("role")
public class RoleController {

	@Inject RoleService roleService;
	@Inject UserService userService;
	
	@RequestMapping(value = {"list"}, method = RequestMethod.GET)
    public String list(Map<String, Object> model, Principal principal){
		model.put("roles",this.roleService.getRolesByUsername(principal.getName()));
		return("role/list");
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String createRole(Map<String, Object> model){
		model.put("role", new Role());
		return ("role/add");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public View createRole(Role role, Principal principal){
		role.setUser(this.userService.findByUsername(principal.getName()));
		this.roleService.save(role);
		return new RedirectView("/role/list", true, false);
	}
	
	@RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
	public View deleteRole(@PathVariable("id") long id){
		this.roleService.delete(id);
		return new RedirectView("/role/list", true, false);
	}
}
