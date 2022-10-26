package com.esperanca.api.salessystem.dtos.roles;

import com.esperanca.api.salessystem.entities.RoleEntity;
import com.esperanca.api.salessystem.enuns.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleOutputDto {
  private Integer id;
  private RoleName roleName;

  public RoleOutputDto(RoleEntity roleEntity) {
    id = roleEntity.getId();
    roleName = roleEntity.getRoleName();
  }
}
