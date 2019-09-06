package com.webapp.site;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
 


import com.webapp.site.entities.UserProfile;
 
/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
@Component
public class RoleToUserProfileConverter implements Converter<Object, UserProfile>{
 
    static final Logger logger = LoggerFactory.getLogger(RoleToUserProfileConverter.class);
     
    @Inject
    UserProfileService userProfileService;
 
    /**
     * Gets UserProfile by Id
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public UserProfile convert(Object element) {
        Long id = Long.parseLong((String)element);
        UserProfile profile= userProfileService.getUserProfile(id);
        logger.info("Profile : {}",profile);
        return profile;
    }
     
}

