package com.esperanca.api.salessystem.services;

import com.esperanca.api.salessystem.dtos.users.UserInputDto;
import com.esperanca.api.salessystem.dtos.users.UserOutputDto;
import com.esperanca.api.salessystem.entities.UserEntity;
import com.esperanca.api.salessystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Transactional
  public UserOutputDto save(UserInputDto userInputDto) {
    var userEntity = new UserEntity();
    String encryptedPassword = passwordEncoder.encode(userInputDto.getPassword());

    userEntity.setUsername(userInputDto.getUsername());
    userEntity.setPassword(encryptedPassword);

    userRepository.save(userEntity);

    return new UserOutputDto(userEntity);
  }

  @Transactional
  public UserOutputDto save(UserInputDto userInputDto, Integer id) {
    UserEntity userEntity = userRepository.findById(id).get();
    String encryptedPassword = passwordEncoder.encode(userInputDto.getPassword());

    userEntity.setUsername(userInputDto.getUsername());
    userEntity.setPassword(encryptedPassword);

    userRepository.save(userEntity);

    return new UserOutputDto(userEntity);
  }

  public List<UserOutputDto> findAll() {
    return userRepository
      .findAll()
      .stream()
      .map(UserOutputDto::new)
      .collect(Collectors.toList());
  }

  public Optional<UserOutputDto> findById(Integer id) {
    return userRepository
      .findById(id)
      .map(UserOutputDto::new);
  }

  @Transactional
  public void deleteById(Integer id) {
    userRepository.deleteById(id);
  }
}
