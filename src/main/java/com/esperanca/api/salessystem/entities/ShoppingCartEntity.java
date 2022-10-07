package com.esperanca.api.salessystem.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Entity
@Table(name = "ShoppingCart")
public class ShoppingCartEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "purchase_product_id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private ProductModel product;

  @ManyToOne
  @JoinColumn(name = "purchase_id")
  private PurchaseModel purchase;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private Float subtotal;

  @Column(nullable = false)
  private LocalDateTime registrationDate;

  @Column(nullable = false)
  private LocalDateTime updateDate;

  public ShoppingCartEntity() {}

  public ShoppingCartEntity(ShoppingCartEntityInputDto shoppingCartEntityInputDto) {
    quantity = shoppingCartEntityInputDto.getQuantity();
    subtotal = shoppingCartEntityInputDto.getSubtotal();
  }

  public void setCurrentDateForInsert() {
    registrationDate = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    updateDate = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
  }

  public void setCurrentDateForUpdate() {
    updateDate = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
  }
}
