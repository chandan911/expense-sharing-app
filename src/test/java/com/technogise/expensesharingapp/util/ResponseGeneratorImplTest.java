package com.technogise.expensesharingapp.util;

import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.models.Expense;
import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.responseModels.DebtResponse;
import com.technogise.expensesharingapp.responseModels.ExpenseDebtResponse;
import com.technogise.expensesharingapp.responseModels.ExpenseResponse;
import com.technogise.expensesharingapp.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
public class ResponseGeneratorImplTest {

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private ResponseGeneratorImpl mockResponseGeneratorImpl;

    @Test
    void testExpenseDebtResponseGenerator() {

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

        Optional<User> optionalUser1 = Optional.of(user1);
        Optional<User> optionalUser2 = Optional.of(user2);
        Optional<User> optionalUser3 = Optional.of(user3);
        Mockito.when(mockUserService.getUserById(1L)).thenReturn(optionalUser1);
        Mockito.when(mockUserService.getUserById(2L)).thenReturn(optionalUser2);
        Mockito.when(mockUserService.getUserById(3L)).thenReturn(optionalUser3);

        User user = new User("chandan", "pass", "9304011010");
        Mockito.when(mockUserService.getUserById(1L)).thenReturn(Optional.of(user));
        ExpenseDebtResponse actualExpenseDebtResponse = mockResponseGeneratorImpl.expenseDebtResponseGenerator(expenses, debts, user);

        List<ExpenseResponse> expenseResponses =
                expenses.stream().map(expense -> mockResponseGeneratorImpl.expenseResponseGenerator(expense)).collect( Collectors.toList() );
        System.out.println(expenseResponses.get(0).getPayerName());
        List<DebtResponse> debtResponses = debts.stream().map(debt ->{
            DebtResponse debtResponse = mockResponseGeneratorImpl.debtResponseGenerator(debt);
            if(debtResponse.getCreditor().equals(user.getName())) debtResponse.setCreditor(null);
            if(debtResponse.getDebtor().equals(user.getName())) debtResponse.setDebtor(null);
            return debtResponse;
        }).collect(Collectors.toList());
        ExpenseDebtResponse expectedExpenseDebtResponse = new ExpenseDebtResponse(expenseResponses, debtResponses);
        Assertions.assertEquals(expectedExpenseDebtResponse, actualExpenseDebtResponse);
    }
}
