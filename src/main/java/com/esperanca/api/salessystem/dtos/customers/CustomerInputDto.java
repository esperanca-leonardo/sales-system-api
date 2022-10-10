package com.esperanca.api.salessystem.dtos.customers;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CustomerInputDto {
  @NotBlank
  private String name;
  @CPF
  private String cpf;
  @NotBlank
  private String cellPhoneNumber;
}
