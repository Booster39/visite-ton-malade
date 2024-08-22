package com.example.sickvisit.service;


import com.example.sickvisit.dto.MessageDto;
import com.example.sickvisit.mapper.MessageMapper;
import com.example.sickvisit.models.Message;
import com.example.sickvisit.models.Profile;
import com.example.sickvisit.models.User;
import com.example.sickvisit.payload.response.MessageResponse;
import com.example.sickvisit.repository.MessageRepository;
import com.example.sickvisit.repository.ProfileRepository;
import com.example.sickvisit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProfileRepository profileRepository;
  @Autowired
  private MessageMapper messageMapper;


  public MessageDto createMessage(MessageResponse messageResponse) {
    User user = userRepository.findById(messageResponse.getUser_id())
            .orElseThrow(() -> new RuntimeException("User not found"));
    Profile profile = profileRepository.findById(messageResponse.getProfile_id())
            .orElseThrow(() -> new RuntimeException("Profile not found"));

    Message message = Message.builder()
            .message(messageResponse.getMessage())
            .user(user)
            .profile(profile)
            .build();

    Message savedMessage = messageRepository.save(message);
    return messageMapper.toDto(savedMessage);
  }
}

