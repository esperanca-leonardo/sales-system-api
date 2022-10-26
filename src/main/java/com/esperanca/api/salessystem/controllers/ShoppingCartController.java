package com.esperanca.api.salessystem.controllers;

import com.esperanca.api.salessystem.dtos.products.ProductOutputDto;
import com.esperanca.api.salessystem.dtos.purchases.PurchaseOutputDto;
import com.esperanca.api.salessystem.dtos.shoppingcarts.ShoppingCartInputDto;
import com.esperanca.api.salessystem.dtos.shoppingcarts.ShoppingCartOutputDto;
import com.esperanca.api.salessystem.services.ProductService;
import com.esperanca.api.salessystem.services.PurchaseService;
import com.esperanca.api.salessystem.services.ShoppingCartService;
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
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
  @Autowired
  private ShoppingCartService shoppingCartService;
  @Autowired
  private ProductService productService;
  @Autowired
  private PurchaseService purchaseService;

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<Object> save(@RequestBody @Valid ShoppingCartInputDto shoppingCartInputDto) {
    Optional<ProductOutputDto> productOutputDtoOptional = productService.findById(shoppingCartInputDto.getProduct());
    Optional<PurchaseOutputDto> purchaseOutputDtoOptional = purchaseService.findById(shoppingCartInputDto.getPurchase());

    if (productOutputDtoOptional.isPresent()) {
      if (purchaseOutputDtoOptional.isPresent()) {
        return ResponseEntity
          .status(HttpStatus.OK)
          .body(shoppingCartService.save(shoppingCartInputDto));
      }
      else {
        return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("Purchase not found!");
      }
    }
    else {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Product not found!");
    }
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN', 'ROLE_COMMON')")
  @GetMapping
  public ResponseEntity<List<ShoppingCartOutputDto>> findAll() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(shoppingCartService.findAll());
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN', 'ROLE_COMMON')")
  @GetMapping(value = "/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") Integer id) {
    Optional<ShoppingCartOutputDto> shoppingCartOutputDtoOptional = shoppingCartService.findById(id);

    if (shoppingCartOutputDtoOptional.isPresent()) {
      return ResponseEntity
        .status(HttpStatus.FOUND)
        .body(shoppingCartOutputDtoOptional.get());
    }
    else {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("ShoppingCart not found!");
    }
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Integer id) {
    Optional<ShoppingCartOutputDto> shoppingCartOutputDtoOptional = shoppingCartService.findById(id);

    if (shoppingCartOutputDtoOptional.isPresent()) {
      shoppingCartService.deleteById(id);

      return ResponseEntity
        .status(HttpStatus.OK)
        .body("PurchaseProduct deleted successfully!");
    }
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("PurchaseProduct not found!");
  }

  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> updateById(@RequestBody @Valid ShoppingCartInputDto shoppingCartInputDto,
                                           @PathVariable(value = "id") Integer id) {

    Optional<PurchaseOutputDto> purchaseOutputDto = purchaseService.findById(shoppingCartInputDto.getPurchase());
    Optional<ProductOutputDto> productOutputDtoOptional = productService.findById(shoppingCartInputDto.getProduct());
    Optional<ShoppingCartOutputDto> purchaseProductOutputDtoOptional = shoppingCartService.findById(id);

    if (purchaseProductOutputDtoOptional.isPresent()) {
      if (purchaseOutputDto.isPresent()) {
        if (productOutputDtoOptional.isPresent()) {
          return ResponseEntity
            .status(HttpStatus.OK)
            .body(shoppingCartService.save(shoppingCartInputDto, id));
        }
        else {
          return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body("Product not found!");
        }
      }
      else {
        return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("Purchase not found!");
      }
    }
    else {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("ShoppingCart not found!");
    }
  }
}
