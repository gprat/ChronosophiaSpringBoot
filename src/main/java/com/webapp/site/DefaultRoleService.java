package com.webapp.site;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import com.webapp.site.entities.Role;
import com.webapp.site.repositories.RoleRepository;

@Service
public class DefaultRoleService implements RoleService {

	@Inject RoleRepository roleRepository;
	
	@Override
	public List<Role> getAllRoles() {
		List<Role> list = new ArrayList<>();
		roleRepository.findAll().forEach(e->list.add(e));
		return list;
	}

	@Override
	public Role getRole(long id, String username) {
		Role role = roleRepository.findById(id).get();
		if(role.getUser().getUsername().equals(username)) {
			return role;
		}
		return null;
	}

	@Override
	public void save(Role role) {
		roleRepository.save(role);
	}

	@Override
	public void delete(long id, String username) {
		Role role = getRole(id, username);
		if(role!=null) {
			roleRepository.deleteById(id);
		}
	}
	
	@Override
	public Role getRole(String name){
		return roleRepository.getOneByName(name);
	}
	
	@Override
	public List<Role> getRolesByUsername(String username){
		return this.roleRepository.findByUser_username(username);
	}
	
	@Override
	public Role getRoleByNameAndUsername(String name, String username) {
		return this.roleRepository.getOneByNameAndUser_username(name, username);
	}

}
