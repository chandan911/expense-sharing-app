package com.technogise.expensesharingapp.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public interface Validator {

  boolean validatePhoneNumber(String phoneNumber);

}
