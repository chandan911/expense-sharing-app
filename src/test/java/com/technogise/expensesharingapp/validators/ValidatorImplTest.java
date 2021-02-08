package com.technogise.expensesharingapp.validators;

import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class ValidatorImplTest {

  @Mock
  private UserService mockUserService;

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

  @Test
  void testvalidateUserIdWithInvalidUser() {
    Optional<User> user = Optional.empty();
    Mockito.when(mockUserService.getUserById(any(Long.class))).thenReturn(user);
    Assertions.assertFalse(validatorImpl.validateUserId(1L));
  }

  @Test
  void testvalidateUserIdWithValidUser() {
      User user = new User();
      Mockito.when(mockUserService.getUserById(any(Long.class))).thenReturn(Optional.of(user));
      Assertions.assertTrue(validatorImpl.validateUserId(1L));
    }
}
