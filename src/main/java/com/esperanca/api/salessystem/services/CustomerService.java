package com.esperanca.api.salessystem.services;

import com.esperanca.api.salessystem.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
  @Autowired
  CustomerRepository customerRepository;

  @Transactional
  public CustomerOutputDto save(CustomerInputDto customerInputDto) {
    CustomerModel customerModel = new CustomerModel(customerInputDto);

    customerModel.setCurrentDateForInsert();
    customerRepository.save(customerModel);

    return new CustomerOutputDto(customerModel);
  }

  @Transactional
  public CustomerOutputDto save(CustomerInputDto customerInputDto, Integer id) {
    CustomerModel customerModel = customerRepository.findById(id).get();

    customerModel.setAttributesForUpdate(customerInputDto);
    customerRepository.save(customerModel);

    return new CustomerOutputDto(customerModel);
  }

  public List<CustomerOutputDto> findAll() {
    return customerRepository
      .findAll()
      .stream()
      .map(CustomerOutputDto::new)
      .collect(Collectors.toList());
  }

  public Optional<CustomerOutputDto> findById(Integer id) {
    return customerRepository
      .findById(id)
      .map(CustomerOutputDto::new);
  }

  @Transactional
  public void deleteById(Integer id) {
    customerRepository.deleteById(id);
  }
}
