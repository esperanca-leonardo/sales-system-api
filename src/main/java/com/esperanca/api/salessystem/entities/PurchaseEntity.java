package com.esperanca.api.salessystem.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@Entity
@Table(name = "Purchase")
public class PurchaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "purchase_id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private CustomerEntity customer;

  @Column(nullable = false)
  private Float total;

  @Column(nullable = false)
  private LocalDateTime registrationDate;

  @Column(nullable = false)
  private LocalDateTime updateDate;

  public PurchaseEntity() {
    total = 0F;

    setCurrentDateForInsert();
  }

  public void setCurrentDateForInsert() {
    setRegistrationDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
    setUpdateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
  }

  public void setCurrentDateForUpdate() {
    setUpdateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
  }
}
