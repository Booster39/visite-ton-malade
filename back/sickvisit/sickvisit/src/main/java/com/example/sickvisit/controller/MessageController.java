package com.example.sickvisit.controller;


import com.example.sickvisit.dto.MessageDto;
import com.example.sickvisit.mapper.MessageMapper;
import com.example.sickvisit.payload.response.MessageResponse;
import com.example.sickvisit.payload.response.StringResponse;
import com.example.sickvisit.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping("/api/messages")
@Log4j2
@Tag(name = "Messages", description = "API pour la gestion des messages")
public class MessageController {

  @Autowired
  private MessageService messageService;

  @Autowired
  private MessageMapper messageMapper;

  @Operation(summary = "Créer un nouveau message", description = "Crée un nouveau message lié à un utilisateur et une location.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Message créé avec succès",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = StringResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requête invalide ou ressources non trouvées",
                  content = @Content)
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@Valid @RequestBody MessageResponse messageResponse) {
    try {
      MessageDto messageDto = messageService.createMessage(messageResponse);
      return new ResponseEntity<>(new StringResponse("Message sent with success"), HttpStatus.CREATED);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
