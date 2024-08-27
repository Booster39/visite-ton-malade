package com.example.sickvisit.controller;


import com.example.sickvisit.dto.ProfileDto;
import com.example.sickvisit.mapper.ProfileMapper;
import com.example.sickvisit.models.Profile;
import com.example.sickvisit.models.User;
import com.example.sickvisit.payload.response.StringResponse;
import com.example.sickvisit.repository.ProfileRepository;
import com.example.sickvisit.repository.UserRepository;
import com.example.sickvisit.security.jwt.JwtUtils;
import com.example.sickvisit.service.FileStorageService;
import com.example.sickvisit.service.ProfileService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
@RestController
@RequestMapping("/api/profiles")
@Log4j2
@Tag(name = "Profiles", description = "API pour la gestion des locations")
public class ProfileController {

  @Autowired
  private ProfileMapper profileMapper;

  @Autowired
  private ProfileService profileService;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProfileRepository profileRepository;

  private final FileStorageService fileStorageService;

  public ProfileController(FileStorageService fileStorageService) {
    this.fileStorageService = fileStorageService;
  }

  @Operation(summary = "Obtenir toutes les locations", description = "Retourne une liste de toutes les locations.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Liste des locations",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileDto.class))),
          @ApiResponse(responseCode = "400", description = "Requête invalide",
                  content = @Content)
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HashMap<String, List<ProfileDto>>> findAll() {
    try {
      List<Profile> profiles = this.profileService.findAll();
      var response = new HashMap<String, List<ProfileDto>>();
      response.put("profiles", this.profileMapper.toDto(profiles));
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(summary = "Obtenir une location par ID", description = "Retourne une location spécifique basée sur l'ID fourni.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Détails de la location",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileDto.class))),
          @ApiResponse(responseCode = "400", description = "Requête invalide",
                  content = @Content),
          @ApiResponse(responseCode = "404", description = "Location non trouvée",
                  content = @Content)
  })
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProfileDto> findById(@PathVariable("id") String id) {
    try {
      Profile profile = this.profileService.findById(Long.valueOf(id));
      if (profile == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok().body(this.profileMapper.toDto(profile));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(summary = "Créer une nouvelle location", description = "Crée une nouvelle location et retourne un message de succès.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Location créée avec succès",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = StringResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requête invalide",
                  content = @Content)
  })
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StringResponse> create(
          @RequestPart("picture") MultipartFile multipartFile,
          @RequestParam("name") @NotBlank @Size(max = 63) String name,
          @RequestParam("age") @Min(0) Long age,
          @RequestParam("city") @Size(max = 2000) String city,
            @RequestParam("description") @Size(max = 2000) String description,
            @RequestHeader(value = "Authorization", required = false) String jwt
  ) {
    try {
      String username = jwtUtils.getUserNameFromJwtToken(jwt.substring(7));
      User owner = this.userRepository.findByEmail(username)
              .orElseThrow(() -> new RuntimeException("User not found"));
      String picturePath = fileStorageService.savePicture(multipartFile);
      Profile profile = profileService.create(name, age, city, description, picturePath, owner);
      return ResponseEntity.ok().body(new StringResponse("Profile created !"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new StringResponse("Error: Invalid request"));
    }
  }

  @Operation(summary = "Mettre à jour une location existante", description = "Met à jour une location existante et retourne un message de succès.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Location mise à jour avec succès",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = StringResponse.class))),
          @ApiResponse(responseCode = "400", description = "Requête invalide",
                  content = @Content),
          @ApiResponse(responseCode = "404", description = "Location non trouvée",
                  content = @Content)
  })
  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> update(
          @PathVariable("id") String id,
          @RequestParam("name") @NotBlank @Size(max = 63) String name,
          @RequestParam("age") @Min(0) Long age,
          @RequestParam("city") @Size(max = 2000) String city,
          @RequestParam("description") @Size(max = 2000) String description,
          @RequestHeader(value = "Authorization", required = false) String jwt
  ) {
    try {
      String username = jwtUtils.getUserNameFromJwtToken(jwt.substring(7));
      User owner = this.userRepository.findByEmail(username)
              .orElseThrow(() -> new RuntimeException("User not found"));

      Profile profile = profileService.update(Long.parseLong(id), name, age, city, description, owner);
      return ResponseEntity.ok().body(new StringResponse("Profile updated !"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new StringResponse("Error: Invalid request"));
        }
  }
}
