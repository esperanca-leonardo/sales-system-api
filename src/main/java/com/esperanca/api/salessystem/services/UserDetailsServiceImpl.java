package com.esperanca.api.salessystem.services;

import com.esperanca.api.salessystem.entities.UserEntity;
import com.esperanca.api.salessystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository
      .findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));

    return new User(
      userEntity.getUsername(),
      userEntity.getPassword(),
      true,
      true,
      true,
      true,
      userEntity.getAuthorities()
    );
  }
}
