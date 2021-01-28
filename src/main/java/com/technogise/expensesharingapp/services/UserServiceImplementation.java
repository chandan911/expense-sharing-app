package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{
  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplementation.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public List<User> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users;
  }

  @Override
  public User createOrUpdate(User user) {
    String originalPassword = user.getPassword();
    String securePassword = passwordEncoder.encode(originalPassword);
    user.setPassword(securePassword);
    return userRepository.save(user);
  }

  @Override
  public Optional<User> getUserByPhoneNumber(String phoneNumber) {
    User user = null;
    for(User u: userRepository.findAll()){
      if(u.getPhoneNumber().equals(phoneNumber)){
        user = u;
        break;
      }
    }
    return Optional.ofNullable(user);
  }

  @Override
  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }
}
