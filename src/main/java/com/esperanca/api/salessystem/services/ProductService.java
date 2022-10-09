package com.esperanca.api.salessystem.services;

import com.esperanca.api.salessystem.dtos.products.ProductInputDto;
import com.esperanca.api.salessystem.dtos.products.ProductOutputDto;
import com.esperanca.api.salessystem.entities.ProductEntity;
import com.esperanca.api.salessystem.repositories.ProductRepository;
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
    ProductEntity productEntity = new ProductEntity(productInputDto);

    productRepository.save(productEntity);

    return new ProductOutputDto(productEntity);
  }

  @Transactional
  public ProductOutputDto save(ProductInputDto productInputDto, Integer id) {
    ProductEntity productEntity = productRepository.findById(id).get();

    productEntity.setAttributesForUpdate(productInputDto);
    productRepository.save(productEntity);

    return new ProductOutputDto(productEntity);
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
