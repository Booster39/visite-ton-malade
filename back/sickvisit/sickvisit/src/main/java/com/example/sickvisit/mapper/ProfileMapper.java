package com.example.sickvisit.mapper;


import com.example.sickvisit.dto.ProfileDto;
import com.example.sickvisit.models.Profile;
import com.example.sickvisit.models.User;
import com.example.sickvisit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileMapper implements EntityMapper<ProfileDto, Profile> {

  @Autowired
  UserRepository userRepository;

  @Override
  public Profile toEntity(ProfileDto dto) {
    if ( dto == null ) {
      return null;
    }

    Profile.ProfileBuilder profile = Profile.builder();

    profile.id( dto.getId() );
    profile.name( dto.getName() );
    profile.surface( dto.getSurface() );
    profile.price( dto.getPrice() );
    profile.picture( dto.getPicture() );
    profile.description( dto.getDescription() );
    profile.created_at(dto.getCreated_at());
    profile.updated_at(dto.getUpdated_at());

    User owner = userRepository.findById(dto.getOwner_id()).orElse(null);
    profile.owner(owner);

    return profile.build();
  }

  @Override
  public ProfileDto toDto(Profile entity) {
    if ( entity == null ) {
      return null;
    }

    ProfileDto profileDto = new ProfileDto();

    profileDto.setId( entity.getId() );
    profileDto.setName( entity.getName() );
    profileDto.setSurface( entity.getSurface() );
    profileDto.setPrice( entity.getPrice() );
    profileDto.setPicture( entity.getPicture() );
    profileDto.setDescription( entity.getDescription() );
    profileDto.setOwner_id(entity.getOwner().getId());
    profileDto.setCreated_at(entity.getCreated_at());
    profileDto.setUpdated_at(entity.getUpdated_at());



    return profileDto;
  }

  @Override
  public List<Profile> toEntity(List<ProfileDto> dtoList) {
    if ( dtoList == null ) {
      return null;
    }

    return dtoList.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }

  @Override
  public List<ProfileDto> toDto(List<Profile> entityList) {
    if ( entityList == null ) {
      return null;
    }

    return entityList.stream()
      .map(this::toDto)
      .collect(Collectors.toList());
  }
}
