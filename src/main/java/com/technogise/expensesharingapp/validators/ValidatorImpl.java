package com.technogise.expensesharingapp.validators;

import org.springframework.stereotype.Service;

@Service
public class ValidatorImpl implements Validator {

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
}
