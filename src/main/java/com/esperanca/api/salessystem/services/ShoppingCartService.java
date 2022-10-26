package com.esperanca.api.salessystem.services;

import com.esperanca.api.salessystem.dtos.shoppingcarts.ShoppingCartInputDto;
import com.esperanca.api.salessystem.dtos.shoppingcarts.ShoppingCartOutputDto;
import com.esperanca.api.salessystem.entities.ProductEntity;
import com.esperanca.api.salessystem.entities.PurchaseEntity;
import com.esperanca.api.salessystem.entities.ShoppingCartEntity;
import com.esperanca.api.salessystem.repositories.ProductRepository;
import com.esperanca.api.salessystem.repositories.PurchaseRepository;
import com.esperanca.api.salessystem.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {
  @Autowired
  private ShoppingCartRepository shoppingCartRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Transactional
  public ShoppingCartOutputDto save(ShoppingCartInputDto shoppingCartInputDto) {
    ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity(shoppingCartInputDto);

    ProductEntity productEntity = productRepository.findById(shoppingCartInputDto.getProduct()).get();
    PurchaseEntity purchaseEntity = purchaseRepository.findById(shoppingCartInputDto.getPurchase()).get();

    Float subtotal = shoppingCartInputDto.getQuantity() * productEntity.getPrice();
    Float total = purchaseEntity.getTotal() + subtotal;

    purchaseEntity.setTotal(total);

    shoppingCartEntity.setSubtotal(subtotal);
    shoppingCartEntity.setProduct(productEntity);
    shoppingCartEntity.setPurchase(purchaseEntity);

    shoppingCartRepository.save(shoppingCartEntity);

    return new ShoppingCartOutputDto(shoppingCartEntity);
  }

  @Transactional
  public ShoppingCartOutputDto save(ShoppingCartInputDto shoppingCartInputDto, Integer id) {
    ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findById(id).get();
    PurchaseEntity currentPurchaseEntity = shoppingCartEntity.getPurchase();
    PurchaseEntity newPurchaseEntity =purchaseRepository.findById(shoppingCartInputDto.getPurchase()).get();
    ProductEntity productEntity = productRepository.findById(shoppingCartInputDto.getProduct()).get();

    float newSubtotal = shoppingCartInputDto.getQuantity() * productEntity.getPrice();
    float oldSubtotal = shoppingCartEntity.getSubtotal();

    currentPurchaseEntity.setTotal(currentPurchaseEntity.getTotal() - oldSubtotal);
    newPurchaseEntity.setTotal(newPurchaseEntity.getTotal() + newSubtotal);

    shoppingCartEntity.setPurchase(newPurchaseEntity);
    shoppingCartEntity.setQuantity(shoppingCartInputDto.getQuantity());
    shoppingCartEntity.setSubtotal(newSubtotal);
    shoppingCartEntity.setProduct(productEntity);
    shoppingCartEntity.setCurrentDateForUpdate();

    shoppingCartRepository.save(shoppingCartEntity);

    return new ShoppingCartOutputDto(shoppingCartEntity);
  }

  public List<ShoppingCartOutputDto> findAll() {
    return shoppingCartRepository
      .findAll()
      .stream()
      .map(ShoppingCartOutputDto::new)
      .collect(Collectors.toList());
  }

  public Optional<ShoppingCartOutputDto> findById(Integer id) {
    return shoppingCartRepository
      .findById(id)
      .map(ShoppingCartOutputDto::new);
  }

  @Transactional
  public void deleteById(Integer id) {
    ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findById(id).get();
    PurchaseEntity purchaseEntity = purchaseRepository.findById(shoppingCartEntity.getPurchase().getId()).get();

    Float newTotal = purchaseEntity.getTotal() - shoppingCartEntity.getSubtotal();

    purchaseEntity.setTotal(newTotal);

    shoppingCartRepository.deleteById(id);
  }
}
