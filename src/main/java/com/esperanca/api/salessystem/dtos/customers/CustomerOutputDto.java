package com.esperanca.api.salessystem.dtos.customers;

import com.esperanca.api.salessystem.entities.CustomerEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerOutputDto {
  private Integer id;
  private String name;
  private String cpf;
  private String cellPhoneNumber;
  private LocalDateTime registrationDate;
  private LocalDateTime updateDate;

  public CustomerOutputDto(CustomerEntity customerEntity) {
    id = customerEntity.getId();
    name = customerEntity.getName();
    cpf = customerEntity.getCpf();
    cellPhoneNumber = customerEntity.getCellPhoneNumber();
    registrationDate = customerEntity.getRegistrationDate();
    updateDate = customerEntity.getUpdateDate();
  }
}
