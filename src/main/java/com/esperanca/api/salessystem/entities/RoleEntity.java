package com.esperanca.api.salessystem.entities;

import com.esperanca.api.salessystem.dtos.roles.RoleInputDto;
import com.esperanca.api.salessystem.enuns.RoleName;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Role")
public class RoleEntity implements GrantedAuthority, Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private RoleName roleName;

  public RoleEntity(RoleInputDto roleInputDto) {
    roleName = roleInputDto.getRoleName();
  }

  @Override
  public String getAuthority() {
    return roleName.name();
  }
}
