package com.esperanca.api.salessystem.controllers;

import com.api.vendas.dtos.products.ProductOutputDto;
import com.api.vendas.dtos.purchase_products.PurchaseProductInputDto;
import com.api.vendas.dtos.purchase_products.PurchaseProductOutputDto;
import com.api.vendas.dtos.purchases.PurchaseOutputDto;
import com.api.vendas.services.ProductService;
import com.api.vendas.services.PurchaseProductService;
import com.api.vendas.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/purchase-products")
public class ShoppingCartController {
  @Autowired
  PurchaseProductService purchaseProductService;
  @Autowired
  ProductService productService;
  @Autowired
  PurchaseService purchaseService;

  @PostMapping
  public ResponseEntity<Object> save(@RequestBody @Valid PurchaseProductInputDto purchaseProductInputDto) {
    Optional<ProductOutputDto> productOutputDtoOptional = productService.findById(purchaseProductInputDto.getProduct());
    Optional<PurchaseOutputDto> purchaseOutputDtoOptional = purchaseService.findById(purchaseProductInputDto.getPurchase());

    if (productOutputDtoOptional.isPresent()) {
      if (purchaseOutputDtoOptional.isPresent()) {
        return ResponseEntity
          .status(HttpStatus.OK)
          .body(purchaseProductService.save(purchaseProductInputDto));
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

  @GetMapping
  public ResponseEntity<List<PurchaseProductOutputDto>> findAll() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(purchaseProductService.findAll());
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") Integer id) {
    Optional<PurchaseProductOutputDto> purchaseProductOutputDtoOptional = purchaseProductService.findById(id);

    if (purchaseProductOutputDtoOptional.isPresent()) {
      return ResponseEntity
        .status(HttpStatus.FOUND)
        .body(purchaseProductOutputDtoOptional.get());
    }
    else {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("PurchaseProduct not found!");
    }
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Integer id) {
    Optional<PurchaseProductOutputDto> purchaseProductOutputDtoOptional = purchaseProductService.findById(id);

    if (purchaseProductOutputDtoOptional.isPresent()) {
      purchaseProductService.deleteById(id);

      return ResponseEntity
        .status(HttpStatus.OK)
        .body("PurchaseProduct deleted successfully!");
    }
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("PurchaseProduct not found!");
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> updateById(@RequestBody @Valid PurchaseProductInputDto purchaseProductInputDto,
                                           @PathVariable(value = "id") Integer id) {

    Optional<PurchaseOutputDto> purchaseOutputDto = purchaseService.findById(purchaseProductInputDto.getPurchase());
    Optional<ProductOutputDto> productOutputDtoOptional = productService.findById(purchaseProductInputDto.getProduct());
    Optional<PurchaseProductOutputDto> purchaseProductOutputDtoOptional = purchaseProductService.findById(id);

    if (purchaseProductOutputDtoOptional.isPresent()) {
      if (purchaseOutputDto.isPresent()) {
        if (productOutputDtoOptional.isPresent()) {
          return ResponseEntity
            .status(HttpStatus.OK)
            .body(purchaseProductService.save(purchaseProductInputDto, id));
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
        .body("PurchaseProduct not found!");
    }
  }
}
