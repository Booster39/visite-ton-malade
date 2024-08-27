package com.example.sickvisit.service;


import com.example.sickvisit.models.Profile;
import com.example.sickvisit.models.User;
import com.example.sickvisit.repository.ProfileRepository;
import com.example.sickvisit.repository.UserRepository;
import com.example.sickvisit.security.jwt.JwtUtils;
import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ProfileService {
  @Autowired
  private ProfileRepository profileRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private FileStorageService fileStorageService;

  public List<Profile> findAll() {
    return this.profileRepository.findAll();
  }

  public Profile findById(Long id) {
    return this.profileRepository.findById(id).orElse(null);
  }

  public Profile create(String name, Long age, String city, String description, String picturePath, User owner) {
    var dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
   // String formattedDateString = owner.getCreated_at().format(dateTimeFormatter);
    Profile profile = Profile.builder()
            .owner(owner)
            .name(name)
            .age(age)
            .city(city)
            .description(description)
            .picture(picturePath)
           // .created_at(LocalDateTime.now())
            .build();
    return this.profileRepository.save(profile);
  }

  public Profile update(Long id, String name, Long age, String city, String description, User owner) {
    Profile existingProfile = this.profileRepository.findById(id).orElse(null);
    if (existingProfile != null) {
      existingProfile.setName(name);
      existingProfile.setAge(age);
      existingProfile.setCity(city);
      existingProfile.setDescription(description);
      existingProfile.setOwner(owner);
     // existingProfile.setCreated_at(owner.getCreated_at());
      return this.profileRepository.save(existingProfile);
    }
    throw new RuntimeException("Profile not found");
  }
}
