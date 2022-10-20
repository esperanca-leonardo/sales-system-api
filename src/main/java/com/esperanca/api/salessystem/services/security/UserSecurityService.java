package com.esperanca.api.salessystem.services.security;

import com.esperanca.api.salessystem.repositories.security.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

  @Autowired
  UserSecurityRepository userSecurityRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userSecurityRepository
      .findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
  }
}
