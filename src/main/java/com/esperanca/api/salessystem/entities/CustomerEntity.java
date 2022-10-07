package com.esperanca.api.salessystem.entities;

import com.esperanca.api.salessystem.dtos.customers.CustomerInputDto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Entity
@Table(name = "Customer")
public class CustomerEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "customer_id")
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String cpf;

  @Column(nullable = false, unique = true)
  private String cellPhoneNumber;

  @Column(nullable = false)
  private LocalDateTime registrationDate;

  @Column(nullable = false)
  private LocalDateTime updateDate;

  public CustomerEntity() {}

  public CustomerEntity(CustomerInputDto customerInputDto) {
    name = customerInputDto.getName();
    cpf = customerInputDto.getCpf();
    cellPhoneNumber = customerInputDto.getCellPhoneNumber();

    setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
    setUpdateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
  }

  public void setCurrentDateForUpdate() {
    this.setUpdateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
  }

  public void setAttributesForUpdate(CustomerInputDto customerInputDto) {
    name = customerInputDto.getName();
    cpf = customerInputDto.getCpf();
    cellPhoneNumber = customerInputDto.getCellPhoneNumber();

    setCurrentDateForUpdate();
  }
}
