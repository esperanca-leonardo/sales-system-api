package com.esperanca.api.salessystem.dtos.products;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ProductInputDto {
  @NotBlank
  private String name;
  private String description;
  @PositiveOrZero
  private Float price;
}
