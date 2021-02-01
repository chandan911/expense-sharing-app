package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("userService")
public interface UserService {

  List<User> getAllUsers();

  User createOrUpdate(User user);

  Optional<User> getUserByPhoneNumber(String phoneNumber);

  Optional<User> getUserById(Long id);

}
