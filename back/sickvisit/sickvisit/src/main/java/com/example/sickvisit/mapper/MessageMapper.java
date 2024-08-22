package com.example.sickvisit.mapper;


import com.example.sickvisit.dto.MessageDto;
import com.example.sickvisit.models.Message;
import com.example.sickvisit.models.Profile;
import com.example.sickvisit.models.User;
import com.example.sickvisit.repository.ProfileRepository;
import com.example.sickvisit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class MessageMapper implements EntityMapper<MessageDto, Message> {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ProfileRepository profileRepository;

  @Override
  public Message toEntity(MessageDto dto) {
    if ( dto == null ) {
      return null;
    }

    Message.MessageBuilder message = Message.builder();

    message.message( dto.getMessage() );

    User user = userRepository.findById(dto.getUser_id()).orElse(null);
    message.user(user);

    Profile profile = profileRepository.findById(dto.getProfile_id()).orElse(null);
    message.profile(profile);

    message.id(dto.getId());
    message.created_at(dto.getCreated_at());
    message.updated_at(dto.getUpdated_at());

    return message.build();
  }

  @Override
  public MessageDto toDto(Message entity) {
    if ( entity == null ) {
      return null;
    }

    MessageDto messageDto = new MessageDto();


    messageDto.setMessage( entity.getMessage() );
    messageDto.setProfile_id(entity.getProfile().getId());
    messageDto.setUser_id(entity.getUser().getId());
    messageDto.setId(entity.getId());
    messageDto.setCreated_at(entity.getCreated_at());
    messageDto.setUpdated_at(entity.getUpdated_at());

    return messageDto;
  }

  @Override
  public List<Message> toEntity(List<MessageDto> dtoList) {
    if ( dtoList == null ) {
      return null;
    }

    return dtoList.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }

  @Override
  public List<MessageDto> toDto(List<Message> entityList) {
    if ( entityList == null ) {
      return null;
    }

    return entityList.stream()
      .map(this::toDto)
      .collect(Collectors.toList());
  }
}
