package com.example.sickvisit.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema(description = "Requête d'inscription de l'utilisateur")
@Data
public class SignupRequest {
  @Schema(description = "Email de l'utilisateur", example = "user@example.com", required = true)
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @Schema(description = "Nom de l'utilisateur", example = "user", required = true)
  @NotBlank
  @Size(min = 3, max = 20)
  private String name;

  @Schema(description = "Mot de passe de l'utilisateur", example = "password123", required = true)
  @NotBlank
  @Size(min = 6, max = 40)
  private String password;
}
