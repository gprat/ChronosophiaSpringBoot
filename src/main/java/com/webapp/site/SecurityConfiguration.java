package com.webapp.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
    @Autowired
    UserDetailsService userDetailsService;
 
	
	  //@Autowired PersistentTokenRepository tokenRepository;
	 
 
	
	  @Autowired public void configureGlobalSecurity(AuthenticationManagerBuilder
	  auth) throws Exception { auth.userDetailsService(userDetailsService);
	  auth.authenticationProvider(authenticationProvider()); }
	 
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.authorizeRequests().antMatchers("/", "/list","/**/list","/**/view","/**/add","/**/update","/**/delete")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
                .antMatchers("/newuser/**", "/delete-user-*").access("hasRole('ROLE_ADMIN')").antMatchers("/edit-user-*")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')").and().formLogin().loginPage("/login").permitAll();
    }
 
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
	
	  @Bean public DaoAuthenticationProvider authenticationProvider() {
	  DaoAuthenticationProvider authenticationProvider = new
	  DaoAuthenticationProvider();
	  authenticationProvider.setUserDetailsService(userDetailsService);
	  authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder()); return
	  authenticationProvider; }
	 
	
	
	/*
	 * @Bean public PersistentTokenBasedRememberMeServices
	 * getPersistentTokenBasedRememberMeServices() {
	 * PersistentTokenBasedRememberMeServices tokenBasedservice = new
	 * PersistentTokenBasedRememberMeServices( "remember-me", userDetailsService,
	 * tokenRepository); return tokenBasedservice; }
	 */
	 
	 
 
	
	  @Bean public AuthenticationTrustResolver getAuthenticationTrustResolver() {
	  return new AuthenticationTrustResolverImpl(); }
	 
    
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
 
}
