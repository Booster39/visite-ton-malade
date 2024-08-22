package com.example.sickvisit.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;



@Schema(description = "Requête pour créer un message")
@Data
public class MessageResponse {

  @Schema(description = "ID de l'utilisateur", example = "1", required = true)
  private Long user_id;

  @Schema(description = "ID de la location", example = "1", required = true)
  private Long profile_id;

  @Schema(description = "Contenu du message", example = "Bonjour, je suis intéressé par votre location.", required = true)
  private String message;

  public MessageResponse(String message, Long user_id, Long profile_id)
  {
    this.message = message;
    this.profile_id = profile_id;
    this.user_id = user_id;
  }
}
