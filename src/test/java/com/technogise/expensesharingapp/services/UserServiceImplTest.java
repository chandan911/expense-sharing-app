package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

  @Mock
  private UserRepository mockUserRepository;

  @Mock
  private BCryptPasswordEncoder mockPasswordEncoder;

  @InjectMocks
  private UserServiceImpl mockUserService;

  @Test
  void testGetAllUsers() {
    User user1 = new User("shubham", "pass_1", "PHONE_NUMBER1");
    User user2 = new User("sahil", "pass_2", "PHONE_NUMBER2");
    User user3 = new User("satyam", "pass_3", "PHONE_NUMBER3");
    List<User> expectedUsers = List.of(user1, user2, user3);

    Mockito.when(mockUserRepository.findAll()).thenReturn(expectedUsers);

    List<User> actualUsers = mockUserService.getAllUsers();

    Assertions.assertEquals(expectedUsers, actualUsers);
  }

  @Test
  void testCreateOrUpdateUser() {
    String originalPassword = "originalPassword";
    String securePassword = "$12.qwertyuiopasdfghjkl.zxcvbnm";
    User userFromRequest = new User("Username", originalPassword, "PhoneNumber");
    User userWithEncryptedPassword = new User("Username", securePassword, "PhoneNumber");
    Mockito.when(mockPasswordEncoder.encode(originalPassword)).thenReturn(securePassword);
    Mockito.when(mockUserRepository.save(userWithEncryptedPassword)).thenReturn(userWithEncryptedPassword);
    User savedUser = mockUserService.createOrUpdate(userFromRequest);
    Assertions.assertEquals(userWithEncryptedPassword, savedUser);
  }
}
