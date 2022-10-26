package com.esperanca.api.salessystem.dtos.roles;

import com.esperanca.api.salessystem.enuns.RoleName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoleInputDto {
  @NotNull
  private RoleName roleName;
}
