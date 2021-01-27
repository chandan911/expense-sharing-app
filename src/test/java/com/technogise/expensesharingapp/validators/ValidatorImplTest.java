package com.technogise.expensesharingapp.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ValidatorImplTest {

  @InjectMocks
  private ValidatorImpl validatorImpl;

  @Test
  void testValidatePhoneNumberWhenPhoneNumberIsCorrect() {
    String phoneNUmber = "9304130255";
    Assertions.assertTrue(validatorImpl.validateUserPhoneNumber(phoneNUmber));
  }

  @Test
  void testValidatePhoneNumberWhenPhoneNumberContainsNonDigit() {
    String phoneNUmber = "930413abcd";
    Assertions.assertFalse(validatorImpl.validateUserPhoneNumber(phoneNUmber));
  }

  @Test
  void testValidatePhoneNumberWhenPhoneNumberLengthIsNotTen() {
    String phoneNUmber = "93041302559";
    Assertions.assertFalse(validatorImpl.validateUserPhoneNumber(phoneNUmber));
  }

  @Test
  void testValidateNameWhenNameIsNotEmpty() {
    String name = "user name";
    Assertions.assertTrue(validatorImpl.validateUserName(name));
  }

  @Test
  void testValidateNameWhenNameIsEmpty() {
    String name = "";
    Assertions.assertFalse(validatorImpl.validateUserName(name));
  }

  @Test
  void testValidateNameWhenPasswordIsNotEmpty() {
    String password = "pass word";
    Assertions.assertTrue(validatorImpl.validateUserName(password));
  }

  @Test
  void testValidateNameWhenPasswordIsEmpty() {
    String password = "";
    Assertions.assertFalse(validatorImpl.validateUserPassword(password));
  }
}
