package com.esperanca.api.salessystem.dtos.products;

import com.esperanca.api.salessystem.entities.ProductEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductOutputDto {
  private Integer id;
  private String name;
  private String description;
  private Float price;
  private LocalDateTime registrationDate;
  private LocalDateTime updateDate;

  public ProductOutputDto(ProductEntity productEntity) {
    id = productEntity.getId();
    name = productEntity.getName();
    description = productEntity.getDescription();
    price = productEntity.getPrice();
    registrationDate = productEntity.getRegistrationDate();
    updateDate = productEntity.getUpdateDate();
  }
}
