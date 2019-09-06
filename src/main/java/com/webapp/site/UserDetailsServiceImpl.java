
  package com.webapp.site;
  
  import com.webapp.site.entities.*; import
  com.webapp.site.repositories.UserRepository; import
  org.springframework.beans.factory.annotation.Autowired; import
  org.springframework.security.core.GrantedAuthority; import
  org.springframework.security.core.authority.SimpleGrantedAuthority; import
  org.springframework.security.core.userdetails.UserDetails; import
  org.springframework.security.core.userdetails.UserDetailsService; import
  org.springframework.security.core.userdetails.UsernameNotFoundException;
  import org.springframework.stereotype.Service; import
  org.springframework.transaction.annotation.Transactional;
  
  import java.util.HashSet; import java.util.Set;
  
  @Service public class UserDetailsServiceImpl implements UserDetailsService{
  
  @Autowired private UserRepository userRepository;
  
  @Override
  
  @Transactional(readOnly = true) public UserDetails loadUserByUsername(String
  username) { User user = userRepository.findOneByUsername(username); if (user
  == null) throw new UsernameNotFoundException(username);
  
  Set<GrantedAuthority> grantedAuthorities = new HashSet<>(); for (UserProfile
  profile : user.getUserProfiles()){ grantedAuthorities.add(new
  SimpleGrantedAuthority(profile.getName())); }
  
  return new
  org.springframework.security.core.userdetails.User(user.getUsername(),
  user.getPassword(), grantedAuthorities); } }
 