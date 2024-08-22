package com.example.sickvisit.controller;


import com.example.sickvisit.dto.UserDto;
import com.example.sickvisit.mapper.UserMapper;
import com.example.sickvisit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Log4j2
@Tag(name = "Users", description = "API pour la gestion des utilisateurs")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  @Operation(summary = "Obtenir un utilisateur par ID", description = "Retourne un utilisateur spécifique basé sur l'ID fourni.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Détails de l'utilisateur",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
          @ApiResponse(responseCode = "400", description = "Requête invalide",
                  content = @Content),
          @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                  content = @Content)
  })
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDto> findById(@PathVariable("id") String id) {
    try {
      UserDto userDto = userService.findUserDtoById(Long.valueOf(id));
      if (userDto == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok().body(userDto);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
