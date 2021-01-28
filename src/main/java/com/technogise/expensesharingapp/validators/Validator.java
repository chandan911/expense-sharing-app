package com.technogise.expensesharingapp.validators;

import org.springframework.stereotype.Component;

@Component("validator")
public interface Validator {

  Boolean validateUserName(String name);

  Boolean validateUserPassword(String password);

  Boolean validateUserPhoneNumber(String phoneNumber);

}
