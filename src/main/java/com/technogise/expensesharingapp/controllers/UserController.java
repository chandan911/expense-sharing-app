package com.technogise.expensesharingapp.controllers;

import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.services.UserService;
import com.technogise.expensesharingapp.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private Validator validator;

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @CrossOrigin(origins = "*")
  @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> createOrUpdate(@RequestBody User user) {
    if (validator.validateUserName(user.getName()) &&
        validator.validateUserPassword(user.getPassword()) &&
        validator.validateUserPhoneNumber(user.getPhoneNumber())) {
      try {
        User returnedUser = userService.createOrUpdate(user);
        return new ResponseEntity<User>(returnedUser, HttpStatus.CREATED);
      } catch (DataIntegrityViolationException exception) {
        return new ResponseEntity<String>("Phone number already exists!", HttpStatus.CONFLICT);
      } catch (RuntimeException exception) {
        LOGGER.error(exception.getMessage(), exception.getCause());
        return new ResponseEntity<String>("An unexpected error occur!", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      return new ResponseEntity<String>("Invalid data!", HttpStatus.BAD_REQUEST);
    }
  }
}
