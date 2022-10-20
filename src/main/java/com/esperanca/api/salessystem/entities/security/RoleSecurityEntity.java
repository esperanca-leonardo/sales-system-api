package com.esperanca.api.salessystem.entities.security;

import com.esperanca.api.salessystem.enuns.RoleName;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Role")
public class RoleSecurityEntity implements GrantedAuthority, Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private RoleName roleName;

  @Override
  public String getAuthority() {
    return roleName.name();
  }
}
