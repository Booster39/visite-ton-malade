package com.example.sickvisit.controller;

import com.example.sickvisit.dto.UserDto;
import com.example.sickvisit.payload.request.LoginRequest;
import com.example.sickvisit.payload.request.SignupRequest;
import com.example.sickvisit.payload.response.JwtResponse;
import com.example.sickvisit.payload.response.StringResponse;
import com.example.sickvisit.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "API pour l'authentification et l'inscription des utilisateurs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Authentifier l'utilisateur", description = "Authentifie l'utilisateur et retourne un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentification réussie",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse("error"));
        }
    }

    @Operation(summary = "Enregistrer un nouvel utilisateur", description = "Crée un nouvel utilisateur et retourne un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription réussie",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide ou utilisateur déjà existant",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            JwtResponse jwtResponse = authService.registerUser(signUpRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{}");
        }
    }

    @Operation(summary = "Obtenir les informations de l'utilisateur authentifié", description = "Retourne les informations de l'utilisateur actuellement authentifié.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informations de l'utilisateur",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide",
                    content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<Optional<UserDto>> me() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<UserDto> userDto = authService.getCurrentUser(auth);
            return ResponseEntity.ok().body(userDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
