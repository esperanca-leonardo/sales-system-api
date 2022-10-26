package com.esperanca.api.salessystem.services;

import com.esperanca.api.salessystem.dtos.customers.CustomerOutputDto;
import com.esperanca.api.salessystem.dtos.customers.CustomerInputDto;
import com.esperanca.api.salessystem.entities.CustomerEntity;
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
  private CustomerRepository customerRepository;

  @Transactional
  public CustomerOutputDto save(CustomerInputDto customerInputDto) {
    CustomerEntity customerEntity = new CustomerEntity(customerInputDto);

    customerRepository.save(customerEntity);

    return new CustomerOutputDto(customerEntity);
  }

  @Transactional
  public CustomerOutputDto save(CustomerInputDto customerInputDto, Integer id) {
    CustomerEntity customerEntity = customerRepository.findById(id).get();

    customerEntity.setAttributesForUpdate(customerInputDto);
    customerRepository.save(customerEntity);

    return new CustomerOutputDto(customerEntity);
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
