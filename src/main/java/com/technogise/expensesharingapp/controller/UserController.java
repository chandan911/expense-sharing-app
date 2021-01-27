package com.technogise.expensesharingapp.controller;

import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.services.UserService;
import com.technogise.expensesharingapp.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private Validator validator;

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> createOrUpdate(@RequestBody User user) {
    if (!validator.validatePhoneNumber(user.getPhoneNumber())) {
      return new ResponseEntity<String>("Invalid data!", HttpStatus.BAD_REQUEST);
    } else {
      try {
        User returnedUser = userService.createOrUpdate(user);
        return new ResponseEntity<User>(returnedUser, HttpStatus.CREATED);
      } catch (Exception dummy) {
        return new ResponseEntity<String>("Phone number already exists!", HttpStatus.CONFLICT);
      }
    }
  }
}
