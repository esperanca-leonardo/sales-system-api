package com.esperanca.api.salessystem.dtos.shoppingcarts;

import com.esperanca.api.salessystem.dtos.products.ProductOutputDto;
import com.esperanca.api.salessystem.dtos.purchases.PurchaseOutputDto;
import com.esperanca.api.salessystem.entities.ShoppingCartEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShoppingCartOutputDto {
  private Integer id;
  private ProductOutputDto productOutputDto;
  private PurchaseOutputDto purchaseOutputDto;
  private Integer quantity;
  private Float subtotal;
  private LocalDateTime registrationDate;
  private LocalDateTime updateDate;

  public ShoppingCartOutputDto() {}

  public ShoppingCartOutputDto(ShoppingCartEntity shoppingCartEntity) {
    id = shoppingCartEntity.getId();
    productOutputDto = new ProductOutputDto(shoppingCartEntity.getProduct());
    purchaseOutputDto = new PurchaseOutputDto(shoppingCartEntity.getPurchase());
    quantity = shoppingCartEntity.getQuantity();
    subtotal = shoppingCartEntity.getSubtotal();
    registrationDate = shoppingCartEntity.getRegistrationDate();
    updateDate = shoppingCartEntity.getUpdateDate();
  }
}
