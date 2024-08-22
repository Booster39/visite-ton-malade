package com.example.sickvisit.payload.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "Requête de connexion de l'utilisateur")
@Data
public class LoginRequest {
  @Schema(description = "Email de l'utilisateur", example = "user@example.com", required = true)
  @NotBlank
  private String email;

  @Schema(description = "Mot de passe de l'utilisateur", example = "password123", required = true)
  @NotBlank
	private String password;
}
