package com.esperanca.api.salessystem.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Entity
@Table(name = "Product")
public class ProductEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "product_id")
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Float price;

  @Column(nullable = false)
  private LocalDateTime registrationDate;

  @Column(nullable = false)
  private LocalDateTime updateDate;

  public ProductEntity() {}

  public ProductEntity(ProductInputDto productInputDto) {
    name = productInputDto.getName();
    description = productInputDto.getDescription();
    price = productInputDto.getPrice();
  }

  public void setCurrentDateForInsert() {
    setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
    setUpdateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
  }

  public void setCurrentDateForUpdate() {
    this.setUpdateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
  }

  public void setAttributesForUpdate(ProductInputDto productInputDto) {
    name = productInputDto.getName();
    description = productInputDto.getDescription();
    price = productInputDto.getPrice();
    setCurrentDateForUpdate();
  }
}