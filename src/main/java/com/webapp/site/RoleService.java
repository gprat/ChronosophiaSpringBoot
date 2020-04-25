package com.webapp.site;

import java.util.List;

import com.webapp.site.entities.Role;

public interface RoleService {

	List<Role> getAllRoles();
	
	Role getRole(long id, String username);
	
	void save(Role role);
	
	void delete (long id, String username);

	Role getRole(String name);
	
	List<Role> getRolesByUsername(String username);

	Role getRoleByNameAndUsername(String name, String username);
}
