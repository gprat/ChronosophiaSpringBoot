package com.webapp.site;

import java.util.List;

import com.webapp.site.entities.User;

public interface UserService {
	
	List<User> getAllUsers();
	
	User getUser(long id);
	
	void save(User user);
	
	void update(User user);
	
	void delete(long id);
	
	User findByUsername(String username);
	
	void deleteUserByUsername(String username);
	
	boolean isUserUsernameUnique(Long id, String username);

}
