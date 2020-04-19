package com.webapp.site;

import java.util.List;

import com.webapp.site.entities.UserProfile;

public interface UserProfileService {

	List<UserProfile> getAllUserProfiles();
	
	UserProfile getUserProfile(Long id);
	
	void save(UserProfile userProfile);
	
	void delete(Long id);
	
	UserProfile getUserProfile(String userProfile);
	
}
