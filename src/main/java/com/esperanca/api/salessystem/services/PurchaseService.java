package com.esperanca.api.salessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
  @Autowired
  PurchaseRepository purchaseRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Transactional
  public PurchaseOutputDto save(PurchaseInputDto purchaseInputDto) {
    CustomerModel customerModel = customerRepository.findById(purchaseInputDto.getCustomer()).get();
    PurchaseModel purchaseModel = new PurchaseModel(purchaseInputDto);

    purchaseModel.setCustomer(customerModel);
    purchaseModel.setCurrentDateForInsert();
    purchaseRepository.save(purchaseModel);

    return new PurchaseOutputDto(purchaseModel);
  }

  @Transactional
  public PurchaseOutputDto save(PurchaseInputDto purchaseInputDto, Integer id) {
    PurchaseModel purchaseModel = purchaseRepository.findById(id).get();
    CustomerModel customerModel = customerRepository.findById(purchaseInputDto.getCustomer()).get();

    purchaseModel.setCustomer(customerModel);
    purchaseModel.setCurrentDateForUpdate();

    purchaseRepository.save(purchaseModel);
    return new PurchaseOutputDto(purchaseModel);
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
