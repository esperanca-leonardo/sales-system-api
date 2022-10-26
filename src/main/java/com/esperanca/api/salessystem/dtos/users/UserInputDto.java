package com.esperanca.api.salessystem.dtos.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserInputDto {
  @NotBlank
  private String username;

  @NotBlank
  private String password;
}
