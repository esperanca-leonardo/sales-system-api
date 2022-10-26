package com.esperanca.api.salessystem.controllers;

import com.esperanca.api.salessystem.services.CustomerService;
import com.esperanca.api.salessystem.dtos.customers.CustomerInputDto;
import com.esperanca.api.salessystem.dtos.customers.CustomerOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/customers")
public class CustomerController {
  @Autowired
  private CustomerService customerService;

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<Object> insert(@RequestBody @Valid CustomerInputDto customerInputDto) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(customerService.save(customerInputDto));
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN', 'ROLE_COMMON')")
  @GetMapping
  public ResponseEntity<List<CustomerOutputDto>> findAll() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(customerService.findAll());
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN', 'ROLE_COMMON')")
  @GetMapping(value = "/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value="id") Integer id) {
    Optional<CustomerOutputDto> customerOutputDtoOptional = customerService.findById(id);

    return customerOutputDtoOptional.isPresent()
      ? ResponseEntity
      .status(HttpStatus.FOUND)
      .body(customerOutputDtoOptional.get())

      : ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("Customer not found");
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Integer id) {
    Optional<CustomerOutputDto> customerOutputDtoOptional = customerService.findById(id);

    if (customerOutputDtoOptional.isPresent()) {
      customerService.deleteById(id);

      return ResponseEntity
        .status(HttpStatus.OK)
        .body("Customer deleted successfully!");
    }
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("Customer not found");
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> updateById(@RequestBody @Valid CustomerInputDto customerInputDto,
                                           @PathVariable(value = "id") Integer id) {

    Optional<CustomerOutputDto> customerOutputDtoOptional = customerService.findById(id);

    return customerOutputDtoOptional.isPresent()
      ? ResponseEntity
        .status(HttpStatus.OK)
        .body(customerService.save(customerInputDto, id))

      : ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Customer not found");
  }
}