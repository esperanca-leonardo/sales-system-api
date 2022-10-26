package com.esperanca.api.salessystem.controllers;

import com.esperanca.api.salessystem.dtos.products.ProductInputDto;
import com.esperanca.api.salessystem.dtos.products.ProductOutputDto;
import com.esperanca.api.salessystem.services.ProductService;
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
@RequestMapping("/products")
public class ProductController {
  @Autowired
  private ProductService productService;

  @PreAuthorize("hasAnyRole('ROLE_STOCKIST', 'ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<Object> insert(@RequestBody @Valid ProductInputDto productInputDto) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(productService.save(productInputDto));
  }

  @PreAuthorize("hasAnyRole('ROLE_STOCKIST', 'ROLE_ADMIN', 'ROLE_COMMON')")
  @GetMapping
  public ResponseEntity<List<ProductOutputDto>> findAll() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(productService.findAll());
  }

  @PreAuthorize("hasAnyRole('ROLE_STOCKIST', 'ROLE_ADMIN', 'ROLE_COMMON')")
  @GetMapping(value = "/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value="id") Integer id) {
    Optional<ProductOutputDto> productOutputDtoOptional = productService.findById(id);

    return productOutputDtoOptional.isPresent()
      ? ResponseEntity
      .status(HttpStatus.FOUND)
      .body(productOutputDtoOptional.get())

      : ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("Product not found");
  }

  @PreAuthorize("hasAnyRole('ROLE_STOCKIST', 'ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Integer id) {
    Optional<ProductOutputDto> productOutputDtoOptional = productService.findById(id);

    if (productOutputDtoOptional.isPresent()) {
      productService.deleteById(id);

      return ResponseEntity
        .status(HttpStatus.OK)
        .body("Product deleted successfully!");
    }
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("Product not found");
  }

  @PreAuthorize("hasAnyRole('ROLE_STOCKIST', 'ROLE_ADMIN')")
  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> updateById(@RequestBody @Valid ProductInputDto productInputDto,
                                           @PathVariable(value = "id") Integer id) {

    Optional<ProductOutputDto> productOutputDtoOptional = productService.findById(id);

    return productOutputDtoOptional.isPresent()
      ? ResponseEntity
      .status(HttpStatus.OK)
      .body(productService.save(productInputDto, id))

      : ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("Customer not found");
  }
}
