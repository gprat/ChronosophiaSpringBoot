/*
 * package com.webapp.site;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * 
 * 
 * import jakarta.inject.Inject;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.core.GrantedAuthority; import
 * org.springframework.security.core.authority.SimpleGrantedAuthority; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * import org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.webapp.site.entities.User; import
 * com.webapp.site.entities.UserProfile;
 * 
 * 
 * @Service public class CustomUserDetailsService implements UserDetailsService{
 * 
 * static final Logger logger =
 * LoggerFactory.getLogger(CustomUserDetailsService.class);
 * 
 * @Inject private UserService userService;
 * 
 * @Transactional(readOnly=true) public UserDetails loadUserByUsername(String
 * login) throws UsernameNotFoundException { User user =
 * userService.findByUsername(login); logger.info("User : {}", user);
 * if(user==null){ logger.info("User not found"); throw new
 * UsernameNotFoundException("Username not found"); } return new
 * org.springframework.security.core.userdetails.User(user.getUsername(),
 * user.getPassword(), true, true, true, true, getGrantedAuthorities(user)); }
 * 
 * 
 * private List<GrantedAuthority> getGrantedAuthorities(User user){
 * List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
 * 
 * for(UserProfile userProfile : user.getUserProfiles()){
 * logger.info("UserProfile : {}", userProfile); authorities.add(new
 * SimpleGrantedAuthority("ROLE_"+userProfile.getName())); }
 * logger.info("authorities : {}", authorities); return authorities; }
 * 
 * }
 * 
 */