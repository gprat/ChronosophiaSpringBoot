package com.webapp.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CharacterEncodingFilter;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration{
 
    @Autowired
    UserDetailsService userDetailsService;
 
	
	  //@Autowired PersistentTokenRepository tokenRepository;
	 
 
	
	  @Autowired public void configureGlobalSecurity(AuthenticationManagerBuilder
	  auth) throws Exception { auth.userDetailsService(userDetailsService);
	  auth.authenticationProvider(authenticationProvider()); }
	 
 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.requiresChannel().requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null).requiresSecure();
        http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
            authorizationManagerRequestMatcherRegistry.requestMatchers("/","/**/list","/**/view","/**/add","/**/update","/**/delete","/**/upload","/**/import","/**/export","/**/download","/**/id/**", "/**/save", "/**/add", "/**/filter","/**/logout")
                .hasRole("USER or ADMIN or DBA")
                .requestMatchers("/newuser/**", "/delete-user-*").hasRole("ADMIN")
                .requestMatchers("/edit-user-*", "/userlist")
                .hasRole("ADMIN or DBA")
                .requestMatchers("/create-user","/resources/**").permitAll()
                .requestMatchers("/login/**").permitAll()
                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
            return http.build();
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
	 

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
 
}
