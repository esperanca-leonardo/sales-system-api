package com.esperanca.api.salessystem.services;

import com.esperanca.api.salessystem.dtos.purchases.PurchaseInputDto;
import com.esperanca.api.salessystem.dtos.purchases.PurchaseOutputDto;
import com.esperanca.api.salessystem.entities.CustomerEntity;
import com.esperanca.api.salessystem.entities.PurchaseEntity;
import com.esperanca.api.salessystem.repositories.CustomerRepository;
import com.esperanca.api.salessystem.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
  @Autowired
  private PurchaseRepository purchaseRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Transactional
  public PurchaseOutputDto save(PurchaseInputDto purchaseInputDto) {
    CustomerEntity customerEntity = customerRepository.findById(purchaseInputDto.getCustomer()).get();
    PurchaseEntity purchaseEntity = new PurchaseEntity();

    purchaseEntity.setCustomer(customerEntity);
    purchaseRepository.save(purchaseEntity);

    return new PurchaseOutputDto(purchaseEntity);
  }

  @Transactional
  public PurchaseOutputDto save(PurchaseInputDto purchaseInputDto, Integer id) {
    PurchaseEntity purchaseEntity = purchaseRepository.findById(id).get();
    CustomerEntity customerEntity = customerRepository.findById(purchaseInputDto.getCustomer()).get();

    purchaseEntity.setCustomer(customerEntity);
    purchaseEntity.setCurrentDateForUpdate();
    purchaseRepository.save(purchaseEntity);

    return new PurchaseOutputDto(purchaseEntity);
  }

  public List<PurchaseOutputDto> findAll() {
    return purchaseRepository
      .findAll()
      .stream()
      .map(PurchaseOutputDto::new)
      .collect(Collectors.toList());
  }

  public Optional<PurchaseOutputDto> findById(Integer id) {
    return purchaseRepository
      .findById(id)
      .map(PurchaseOutputDto::new);
  }

  @Transactional
  public void deleteById(Integer id) { purchaseRepository.deleteById(id); }
}
