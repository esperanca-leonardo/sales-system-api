package com.esperanca.api.salessystem.services;

import com.esperanca.api.salessystem.dtos.roles.RoleInputDto;
import com.esperanca.api.salessystem.dtos.roles.RoleOutputDto;
import com.esperanca.api.salessystem.entities.RoleEntity;
import com.esperanca.api.salessystem.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
  @Autowired
  private RoleRepository roleRepository;

  @Transactional
  public RoleOutputDto save(RoleInputDto roleInputDto) {
    var roleSecurity = new RoleEntity(roleInputDto);

    roleRepository.save(roleSecurity);

    return new RoleOutputDto(roleSecurity);
  }

  @Transactional
  public RoleOutputDto save(RoleInputDto roleInputDto, Integer id) {
    RoleEntity roleEntity = roleRepository.findById(id).get();

    roleEntity.setRoleName(roleInputDto.getRoleName());

    roleRepository.save(roleEntity);

    return new RoleOutputDto(roleEntity);
  }

  public List<RoleOutputDto> findAll() {
    return roleRepository
      .findAll()
      .stream()
      .map(RoleOutputDto::new)
      .collect(Collectors.toList());
  }

  public Optional<RoleOutputDto> findById(Integer id) {
    return roleRepository
      .findById(id)
      .map(RoleOutputDto::new);
  }

  @Transactional
  public void deleteById(Integer id) {
    roleRepository.deleteById(id);
  }
}
