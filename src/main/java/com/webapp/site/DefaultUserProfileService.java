package com.webapp.site;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.webapp.site.entities.UserProfile;
import com.webapp.site.repositories.UserProfileRepository;

@Service
public class DefaultUserProfileService implements UserProfileService {

	@Inject UserProfileRepository userProfileRepository;
	
	@Override
	public List<UserProfile> getAllUserProfiles(){
		List<UserProfile> list = new ArrayList<UserProfile>();
		userProfileRepository.findAll().forEach(e->list.add(e));
		return list;
	}
	
	@Override
	public UserProfile getUserProfile(Long id){
		return userProfileRepository.findById(id).get();
	}
	
	@Override
	public void save(UserProfile userProfile){
		userProfileRepository.save(userProfile);
	}
	
	@Override
	public void delete(Long id){
		userProfileRepository.deleteById(id);
	}
	
	@Override
	public UserProfile getUserProfile(String userProfile) {
		return userProfileRepository.findOneByName(userProfile);
	}
}
