package com.esperanca.api.salessystem.dtos.shoppingcarts;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ShoppingCartInputDto {
  @Positive
  private Integer product;

  @Positive
  private Integer purchase;

  @PositiveOrZero
  private Integer quantity;
}
