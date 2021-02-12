package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.Expense;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("expenseService")
public interface ExpenseService {

  List<Expense> getAllExpensesByUserId(Long userId);

  Expense createExpense(Expense expense);
}
