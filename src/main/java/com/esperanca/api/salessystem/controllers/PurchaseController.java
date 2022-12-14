package com.esperanca.api.salessystem.controllers;

import com.esperanca.api.salessystem.dtos.customers.CustomerOutputDto;
import com.esperanca.api.salessystem.dtos.purchases.PurchaseInputDto;
import com.esperanca.api.salessystem.dtos.purchases.PurchaseOutputDto;
import com.esperanca.api.salessystem.services.CustomerService;
import com.esperanca.api.salessystem.services.PurchaseService;
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
@RequestMapping("/purchases")
public class PurchaseController {
  @Autowired
  private PurchaseService purchaseService;
  @Autowired
  private CustomerService customerService;

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<Object> save(@RequestBody @Valid PurchaseInputDto purchaseInputDto) {
    Optional<CustomerOutputDto> customerOutputDtoOptional = customerService.findById(purchaseInputDto.getCustomer());

    return customerOutputDtoOptional.isPresent()
      ? ResponseEntity
        .status(HttpStatus.OK)
        .body(purchaseService.save(purchaseInputDto))

      : ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("Customer id not found!");
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN', 'ROLE_COMMON')")
  @GetMapping
  public ResponseEntity<List<PurchaseOutputDto>> findAll() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(purchaseService.findAll());
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN', 'ROLE_COMMON')")
  @GetMapping(value = "/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") Integer id) {
    Optional<PurchaseOutputDto> purchaseOutputDtoOptional = purchaseService.findById(id);

    return purchaseOutputDtoOptional.isPresent()
      ? ResponseEntity
        .status(HttpStatus.FOUND)
        .body(purchaseOutputDtoOptional.get())

      : ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("Purchase not found!");
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Integer id) {
    Optional<PurchaseOutputDto> purchaseOutputDtoOptional = purchaseService.findById(id);

    if (purchaseOutputDtoOptional.isPresent()) {
      purchaseService.deleteById(id);

      return ResponseEntity
        .status(HttpStatus.OK)
        .body("Purchase deleted successfully!");
    }
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("Purchase not found!");
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> updateById(@RequestBody @Valid PurchaseInputDto purchaseInputDto,
                                           @PathVariable(value = "id") Integer id) {

    Optional<CustomerOutputDto> customerOutputDtoOptional = customerService.findById(purchaseInputDto.getCustomer());
    Optional<PurchaseOutputDto> purchaseOutputDtoOptional = purchaseService.findById(id);

    if (purchaseOutputDtoOptional.isPresent()) {
      if (customerOutputDtoOptional.isPresent()) {
        return ResponseEntity
          .status(HttpStatus.OK)
          .body(purchaseService.save(purchaseInputDto, id));
      }
      else {
        return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("Customer not found!");
      }
    }
    else {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Purchase not found!");
    }
  }
}
