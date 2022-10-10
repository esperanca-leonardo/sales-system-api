package com.esperanca.api.salessystem.dtos.purchases;

import com.esperanca.api.salessystem.dtos.customers.CustomerOutputDto;
import com.esperanca.api.salessystem.entities.PurchaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseOutputDto {
  private Integer id;
  private CustomerOutputDto customerOutputDto;
  private Float total;
  private LocalDateTime registrationDate;
  private LocalDateTime updateDate;

  public PurchaseOutputDto(PurchaseEntity purchaseEntity) {
    id = purchaseEntity.getId();
    customerOutputDto = new CustomerOutputDto(purchaseEntity.getCustomer());
    total = purchaseEntity.getTotal();
    registrationDate = purchaseEntity.getRegistrationDate();
    updateDate = purchaseEntity.getUpdateDate();
  }
}
