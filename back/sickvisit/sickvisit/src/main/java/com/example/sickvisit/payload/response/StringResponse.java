package com.example.sickvisit.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Réponse générique contenant un message")
@Data
public class StringResponse {
  @Schema(description = "Message de la réponse", example = "Message sent with success")
  private String message;

  public StringResponse(String message)
  {
    this.message = message;
  }
}
