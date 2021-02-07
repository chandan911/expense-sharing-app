package com.technogise.expensesharingapp.validators;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("validator")
public interface Validator {

  Boolean validateUserName(String name);

  Boolean validateUserPassword(String password);

  Boolean validateUserPhoneNumber(String phoneNumber);

  Boolean validateUserId(Long payerId);

  Boolean validateDebtorList(ArrayList<Long> debtorId);

}
