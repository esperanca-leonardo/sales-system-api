package com.esperanca.api.salessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {
  @Autowired
  PurchaseProductRepository purchaseProductRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  PurchaseRepository purchaseRepository;

  @Transactional
  public PurchaseProductOutputDto save(PurchaseProductInputDto purchaseProductInputDto) {
    PurchaseProductModel purchaseProductModel = new PurchaseProductModel(purchaseProductInputDto);

    ProductModel productModel = productRepository.findById(purchaseProductInputDto.getProduct()).get();
    PurchaseModel purchaseModel = purchaseRepository.findById(purchaseProductInputDto.getPurchase()).get();

    purchaseProductModel.setProduct(productModel);
    purchaseProductModel.setPurchase(purchaseModel);
    purchaseProductModel.setCurrentDateForInsert();

    purchaseProductRepository.save(purchaseProductModel);

    return new PurchaseProductOutputDto(purchaseProductModel);
  }

  @Transactional
  public PurchaseProductOutputDto save(PurchaseProductInputDto purchaseProductInputDto, Integer id) {
    PurchaseProductModel purchaseProductModel = purchaseProductRepository.findById(id).get();
    PurchaseModel purchaseModel = purchaseRepository.findById(purchaseProductInputDto.getPurchase()).get();
    ProductModel productModel = productRepository.findById(purchaseProductInputDto.getProduct()).get();

    purchaseProductModel.setPurchase(purchaseModel);
    purchaseProductModel.setProduct(productModel);
    purchaseProductModel.setCurrentDateForUpdate();

    purchaseProductRepository.save(purchaseProductModel);

    return new PurchaseProductOutputDto(purchaseProductModel);
  }

  public List<PurchaseProductOutputDto> findAll() {
    return purchaseProductRepository
      .findAll()
      .stream()
      .map(PurchaseProductOutputDto::new)
      .collect(Collectors.toList());
  }

  public Optional<PurchaseProductOutputDto> findById(Integer id) {
    return purchaseProductRepository
      .findById(id)
      .map(PurchaseProductOutputDto::new);
  }

  @Transactional
  public void deleteById(Integer id) {
    purchaseProductRepository.deleteById(id);
  }
}
