package com.technogise.expensesharingapp.validators;

import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidatorImpl implements Validator {

  @Autowired
  private UserService userService;

  @Override
  public Boolean validateUserName(String name) {
    return name.length() != 0;
  }

  @Override
  public Boolean validateUserPassword(String password) {
    return password.length() != 0;
  }

  @Override
  public Boolean validateUserPhoneNumber(String phoneNumber) {
    if (phoneNumber.length() != 10)
      return false;
    else {
      for (int index = 0; index < 10; index++)
        if (phoneNumber.charAt(index) < '0' || phoneNumber.charAt(index) > '9')
          return false;
    }
    return true;
  }

  @Override
  public Boolean validateUserId(Long payerId) {
    Optional<User> user = userService.getUserById(payerId);
    return user.isPresent();
  }

}

