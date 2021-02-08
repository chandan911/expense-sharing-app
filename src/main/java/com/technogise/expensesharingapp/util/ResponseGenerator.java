package com.technogise.expensesharingapp.util;

import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.models.Expense;
import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.responseModels.AggregateDataResponse;
import com.technogise.expensesharingapp.responseModels.DebtResponse;
import com.technogise.expensesharingapp.responseModels.ExpenseResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("responseGenerator")
public interface ResponseGenerator {

  ExpenseResponse expenseResponseGenerator(Expense expense);

  DebtResponse debtResponseGenerator(Debt debt);

  AggregateDataResponse aggregateResponseGenerator(List<Expense> expenses, List<Debt> debts, User user, List<User> allUsers);

}
