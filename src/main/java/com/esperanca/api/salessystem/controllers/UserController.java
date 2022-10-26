package com.esperanca.api.salessystem.controllers;

import com.esperanca.api.salessystem.dtos.users.UserInputDto;
import com.esperanca.api.salessystem.dtos.users.UserOutputDto;
import com.esperanca.api.salessystem.services.UserService;
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
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<Object> save(@RequestBody @Valid UserInputDto userInputDto) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(userService.save(userInputDto));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping
  public ResponseEntity<List<UserOutputDto>> findAll() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(userService.findAll());
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") Integer id) {
    Optional<UserOutputDto> userSecurityOutputDtoOptional = userService.findById(id);

    return userSecurityOutputDtoOptional.isPresent()
      ? ResponseEntity
        .status(HttpStatus.FOUND)
        .body(userSecurityOutputDtoOptional.get())

      : ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("User not found!");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Integer id) {
    Optional<UserOutputDto> userSecurityOutputDtoOptional = userService.findById(id);

    if (userSecurityOutputDtoOptional.isPresent()) {
      userService.deleteById(id);

      return ResponseEntity
        .status(HttpStatus.OK)
        .body("User deleted successfully");
    }
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("User not found!");
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> updateById(@RequestBody @Valid UserInputDto userInputDto,
                                           @PathVariable(value = "id") Integer id) {

    Optional<UserOutputDto> userSecurityOutputDtoOptional = userService.findById(id);

    return userSecurityOutputDtoOptional.isPresent()
      ? ResponseEntity
        .status(HttpStatus.OK)
        .body(userService.save(userInputDto, id))

      : ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("User not found!");
  }
}
