package com.example.sickvisit.service;


import com.example.sickvisit.dto.UserDto;
import com.example.sickvisit.mapper.UserMapper;
import com.example.sickvisit.models.User;
import com.example.sickvisit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper userMapper;


  public User findById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  public UserDto findUserDtoById(Long id) {
    User user = findById(id);
    if (user == null) {
      return null;
    }
    return userMapper.toDto(user);
  }
}
