package com.esperanca.api.salessystem.controllers;

import com.esperanca.api.salessystem.dtos.roles.RoleInputDto;
import com.esperanca.api.salessystem.dtos.roles.RoleOutputDto;
import com.esperanca.api.salessystem.services.RoleService;
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
@RequestMapping("/roles")
public class RoleController {
  @Autowired
  private RoleService roleService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<Object> insert(@RequestBody @Valid RoleInputDto roleInputDto) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(roleService.save(roleInputDto));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping
  public ResponseEntity<List<RoleOutputDto>> findAll() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(roleService.findAll());
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(value = "/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value="id") Integer id) {
    Optional<RoleOutputDto> roleOutputDtoOptional = roleService.findById(id);

    return roleOutputDtoOptional.isPresent()
      ? ResponseEntity
        .status(HttpStatus.FOUND)
        .body(roleOutputDtoOptional.get())

      : ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Role not found!");
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Integer id) {
    Optional<RoleOutputDto> roleOutputDtoOptional = roleService.findById(id);

    if (roleOutputDtoOptional.isPresent()) {
      roleService.deleteById(id);

      return ResponseEntity
        .status(HttpStatus.OK)
        .body("Role deleted successfully!");
    }
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("Role not found!");
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> updateById(@RequestBody @Valid RoleInputDto roleInputDto,
                                           @PathVariable(value = "id") Integer id) {

    Optional<RoleOutputDto> roleOutputDtoOptional = roleService.findById(id);

    return roleOutputDtoOptional.isPresent()
      ? ResponseEntity
        .status(HttpStatus.OK)
        .body(roleService.save(roleInputDto, id))

      : ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Role not found!");
  }
}
