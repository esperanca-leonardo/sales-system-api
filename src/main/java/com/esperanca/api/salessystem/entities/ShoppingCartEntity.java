package com.esperanca.api.salessystem.entities;

import com.esperanca.api.salessystem.dtos.shoppingcarts.ShoppingCartInputDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
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
  private ProductEntity product;

  @ManyToOne
  @JoinColumn(name = "purchase_id")
  private PurchaseEntity purchase;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private Float subtotal;

  @Column(nullable = false)
  private LocalDateTime registrationDate;

  @Column(nullable = false)
  private LocalDateTime updateDate;

  public ShoppingCartEntity() {}

  public ShoppingCartEntity(ShoppingCartInputDto shoppingCartEntityInputDto) {
    quantity = shoppingCartEntityInputDto.getQuantity();
    subtotal = 0F;

    setCurrentDateForInsert();
  }

  public void setCurrentDateForInsert() {
    registrationDate = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

    setCurrentDateForUpdate();
  }

  public void setCurrentDateForUpdate() {
    updateDate = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
  }
}
