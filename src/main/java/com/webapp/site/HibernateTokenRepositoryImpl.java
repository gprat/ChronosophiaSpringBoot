package com.webapp.site;

import java.util.Date;

import jakarta.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.site.entities.*;
import com.webapp.site.repositories.PersistentLoginRepository;
 


@Repository("tokenRepositoryDao")
@Transactional
public class HibernateTokenRepositoryImpl
        implements PersistentTokenRepository {

	@Inject PersistentLoginRepository PersistentLoginRepository;
	
    static final Logger logger = LoggerFactory.getLogger(HibernateTokenRepositoryImpl.class);
 
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        logger.info("Creating Token for user : {}", token.getUsername());
        PersistentLogin PersistentLogin = new PersistentLogin();
        PersistentLogin.setUsername(token.getUsername());
        PersistentLogin.setSeries(token.getSeries());
        PersistentLogin.setToken(token.getTokenValue());
        PersistentLogin.setLast_used(token.getDate());
        PersistentLoginRepository.save(PersistentLogin);
 
    }
 
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        logger.info("Fetch Token if any for seriesId : {}", seriesId);
        try {
            PersistentLogin PersistentLogin = PersistentLoginRepository.findById(seriesId).get();
 
            return new PersistentRememberMeToken(PersistentLogin.getUsername(), PersistentLogin.getSeries(),
                    PersistentLogin.getToken(), PersistentLogin.getLast_used());
        } catch (Exception e) {
            logger.info("Token not found...");
            return null;
        }
    }
 
    @Override
    public void removeUserTokens(String username) {
        logger.info("Removing Token if any for user : {}", username);
        
        PersistentLogin PersistentLogin = PersistentLoginRepository.findOneByUsername(username);
        if (PersistentLogin != null) {
            logger.info("rememberMe was selected");
            PersistentLoginRepository.delete(PersistentLogin);
        }
 
    }
 
    @Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        logger.info("Updating Token for seriesId : {}", seriesId);
        PersistentLogin PersistentLogin = PersistentLoginRepository.findById(seriesId).get();
        PersistentLogin.setToken(tokenValue);
        PersistentLogin.setLast_used(lastUsed);
        PersistentLoginRepository.save(PersistentLogin);
    }
 
}