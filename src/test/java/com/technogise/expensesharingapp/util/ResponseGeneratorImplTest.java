package com.technogise.expensesharingapp.util;

import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.models.Expense;
import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.responseModels.AggregateDataResponse;
import com.technogise.expensesharingapp.responseModels.DebtResponse;
import com.technogise.expensesharingapp.responseModels.ExpenseResponse;
import com.technogise.expensesharingapp.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class ResponseGeneratorImplTest {

  @Mock
  private UserService mockUserService;

  @InjectMocks
  private ResponseGeneratorImpl mockResponseGeneratorImpl;

  @Test
  void testExpenseResponseGenerator() {
    Expense expense = new Expense("Movie", 2400.0, 2L);

    User user = new User("chandan", "pass", "9304011010");
    Optional<User> optionalUser = Optional.of(user);
    Mockito.when(mockUserService.getUserById(any(Long.class))).thenReturn(optionalUser);

    ExpenseResponse expectedExpenseResponse = new ExpenseResponse();
    expectedExpenseResponse.setDescription(expense.getDescription());
    expectedExpenseResponse.setAmount(expense.getAmount());
    expectedExpenseResponse.setPayerName(mockUserService.getUserById(expense.getPayerId()).get().getName());
    expectedExpenseResponse.setDateTime(expense.getCreatedAt());

    Assertions.assertEquals(expectedExpenseResponse, mockResponseGeneratorImpl.expenseResponseGenerator(expense));
  }

  @Test
  void testDebtResponseGenerator() {
    Debt debt = new Debt(1L, 4L, 300.0);

    User user = new User("chandan", "pass", "9304011010");
    Optional<User> optionalUser = Optional.of(user);
    Mockito.when(mockUserService.getUserById(any(Long.class))).thenReturn(optionalUser);

    DebtResponse expectedDebtResponse = new DebtResponse();
    expectedDebtResponse.setId(debt.getId());
    expectedDebtResponse.setAmount(debt.getAmount());
    expectedDebtResponse.setCreditor(mockUserService.getUserById(debt.getCreditorId()).get().getName());
    expectedDebtResponse.setDebtor(mockUserService.getUserById(debt.getDebtorId()).get().getName());

    Assertions.assertEquals(expectedDebtResponse, mockResponseGeneratorImpl.debtResponseGenerator(debt));
  }

  @Test
  void testAggregateResponseGenerator() {

    Expense expense1 = new Expense("Movie", 2600.0, 1L);
    Expense expense2 = new Expense("Dinner", 2800.0, 2L);
    Expense expense3 = new Expense("Lunch", 1700.0, 3L);
    List<Expense> expenses = List.of(expense1, expense2, expense3);

    Debt debt1 = new Debt(1L, 2L, 105.0);
    Debt debt2 = new Debt(2L, 3L, 210.0);
    Debt debt3 = new Debt(3L, 1L, 180.0);
    List<Debt> debts = List.of(debt1, debt2, debt3);

    User user1 = new User("chandan", "pass", "9304011010");
    User user2 = new User("sahil", "pass_2", "9304012346");
    User user3 = new User("satyam", "pass_3", "9304012347");
    List<User> users = new ArrayList<User>() {{
      add(user1);
      add(user2);
      add(user3);
    }};

    Optional<User> optionalUser1 = Optional.of(user1);
    Optional<User> optionalUser2 = Optional.of(user2);
    Optional<User> optionalUser3 = Optional.of(user3);
    Mockito.when(mockUserService.getUserById(1L)).thenReturn(optionalUser1);
    Mockito.when(mockUserService.getUserById(2L)).thenReturn(optionalUser2);
    Mockito.when(mockUserService.getUserById(3L)).thenReturn(optionalUser3);

    AggregateDataResponse actualAggregateDataResponse = mockResponseGeneratorImpl.aggregateResponseGenerator(expenses, debts, user1, users);

    List<ExpenseResponse> expenseResponses = new ArrayList<ExpenseResponse>();
    for (int index = 0; index < expenses.size(); index++)
      expenseResponses.add(mockResponseGeneratorImpl.expenseResponseGenerator(expenses.get(index)));
    List<DebtResponse> debtResponses = new ArrayList<DebtResponse>();
    for (int index = 0; index < debts.size(); index++) {
      debtResponses.add(mockResponseGeneratorImpl.debtResponseGenerator(debts.get(index)));
      if (debtResponses.get(index).getCreditor() == user1.getName()) debtResponses.get(index).setCreditor(null);
      if (debtResponses.get(index).getDebtor() == user1.getName()) debtResponses.get(index).setDebtor(null);
    }
    users.remove(user1);
    AggregateDataResponse expectedAggregateDataResponse = new AggregateDataResponse(expenseResponses, debtResponses, user1, users);

    Assertions.assertEquals(expectedAggregateDataResponse, actualAggregateDataResponse);
  }
}
