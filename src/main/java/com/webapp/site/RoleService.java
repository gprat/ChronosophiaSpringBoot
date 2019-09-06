package com.webapp.site;

import java.util.List;

import com.webapp.site.entities.Role;

public interface RoleService {

	List<Role> getAllRoles();
	
	Role getRole(long id);
	
	void save(Role role);
	
	void delete (long id);

	Role getRole(String name);
	
	List<Role> getRolesByUsername(String username);
}
