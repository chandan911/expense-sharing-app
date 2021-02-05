package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.Expense;
import com.technogise.expensesharingapp.repositories.ExpenseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class ExpenseServiceImplTest {

  @Mock
  private ExpenseRepository mockExpenseRepository;

  @InjectMocks
  private ExpenseServiceImpl mockExpenseServiceImpl;

  @Test
  void testGetAllExpensesByUserId() {
    Expense expense1 = new Expense("Movie", 700.0, 4L);
    Expense expense2 = new Expense("Dinner", 2100.0, 4L);
    Expense expense3 = new Expense("Picnic", 3000.0, 4L);
    List<Expense> expectedExpenses = List.of(expense1, expense2, expense3);

    Mockito.when(mockExpenseRepository.getAllExpensesById(any(Long.class))).thenReturn(expectedExpenses);

    List<Expense> actualExpenses = mockExpenseServiceImpl.getAllExpensesByUserId(4L);

    Assertions.assertEquals(expectedExpenses, actualExpenses);
  }
}
