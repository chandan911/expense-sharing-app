package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.models.Expense;
import com.technogise.expensesharingapp.models.ExpenseDebtor;
import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.repositories.DebtRepository;
import com.technogise.expensesharingapp.repositories.ExpenseDebtorRepository;
import com.technogise.expensesharingapp.repositories.ExpenseRepository;
import com.technogise.expensesharingapp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

  @Mock
  private UserRepository mockUserRepository;

  @Mock
  private BCryptPasswordEncoder mockPasswordEncoder;

  @Mock
  private DebtRepository mockDebtRepository;

  @InjectMocks
  private UserServiceImpl mockUserService;

  @InjectMocks
  private ExpenseServiceImpl mockExpenseService;

  @InjectMocks
  private ExpenseDebtServiceImpl mockExpenseDebtService;

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

  @Test
  void testUpdateDebtRepositoryWhenDebtorIsInDebt() {

    Debt debt1 = new Debt(1L, 2L, 10.0);
//    Debt debt2 = new Debt(2L, 1L, 10.0);
    Optional<Debt> optionalDebt1 = Optional.of(debt1);
    Optional<Debt> optionalDebt2 = Optional.of(null);
    Mockito.when(mockDebtRepository.getCreditorDebtorPair(1L,2L)).thenReturn(optionalDebt1);
    Mockito.when(mockDebtRepository.getCreditorDebtorPair(2L,1L)).thenReturn(optionalDebt2);
//    Double newAmount = debtAmount+debt1.getAmount();
    Mockito.when(mockDebtRepository.updateDebt(any(), any(), any(), any())).thenReturn(1L);

  }

  @Test
  void testUpdateDebtRepositoryWhenPayerIsInDebt() {

    Debt debt2 = new Debt(1L, 2L, 10.0);
//    Debt debt2 = new Debt(2L, 1L, 10.0);
    Optional<Debt> optionalDebt1 = Optional.of(null);
    Optional<Debt> optionalDebt2 = Optional.of(debt2);
    Mockito.when(mockDebtRepository.getCreditorDebtorPair(1L,2L)).thenReturn(optionalDebt1);
    Mockito.when(mockDebtRepository.getCreditorDebtorPair(2L,1L)).thenReturn(optionalDebt2);
//    Double newAmount = debtAmount+debt1.getAmount();
    Mockito.when(mockDebtRepository.updateDebt(any(), any(), any(), any())).thenReturn(1L);

  }
}
