package com.technogise.expensesharingapp.validator;

import org.springframework.stereotype.Component;

@Component("validator")
public class ValidatorImplementation implements Validator {
  @Override
  public boolean validatePhoneNumber(String phoneNumber) {
    if(phoneNumber.length()!=10) return false;
    else
    {
      for(int index = 0; index <10; index++)
        if(phoneNumber.charAt(index)<'0' || phoneNumber.charAt(index)>'9')
          return false;
    }

    return true;
  }
}
