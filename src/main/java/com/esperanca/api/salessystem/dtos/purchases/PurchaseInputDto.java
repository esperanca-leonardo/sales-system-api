package com.esperanca.api.salessystem.dtos.purchases;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class PurchaseInputDto {
  @Positive
  private Integer customer;
}
