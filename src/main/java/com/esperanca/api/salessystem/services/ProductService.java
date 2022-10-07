package com.esperanca.api.salessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
  @Autowired
  ProductRepository productRepository;
  @Transactional
  public ProductOutputDto save(ProductInputDto productInputDto) {
    ProductModel productModel = new ProductModel(productInputDto);
    productModel.setCurrentDateForInsert();
    productRepository.save(productModel);

    return new ProductOutputDto(productModel);
  }

  @Transactional
  public ProductOutputDto save(ProductInputDto productInputDto, Integer id) {
    ProductModel productModel = productRepository.findById(id).get();
    productModel.setAttributesForUpdate(productInputDto);
    productRepository.save(productModel);

    return new ProductOutputDto(productModel);
  }

  public List<ProductOutputDto> findAll() {
    return productRepository
      .findAll()
      .stream()
      .map(ProductOutputDto::new)
      .collect(Collectors.toList());
  }

  public Optional<ProductOutputDto> findById(Integer id) {
    return productRepository
      .findById(id)
      .map(ProductOutputDto::new);
  }
  @Transactional
  public void deleteById(Integer id) {
    productRepository.deleteById(id);
  }
}
