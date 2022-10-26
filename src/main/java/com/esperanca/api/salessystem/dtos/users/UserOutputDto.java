package com.esperanca.api.salessystem.dtos.users;

import com.esperanca.api.salessystem.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOutputDto {
  private Integer id;
  private String username;
  private String password;
  public UserOutputDto(UserEntity userEntity) {
    id = userEntity.getId();
    username = userEntity.getUsername();
    password = userEntity.getPassword();
  }
}
