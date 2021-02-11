package com.technogise.expensesharingapp.util;

import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.models.Expense;
import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.responseModels.AggregateDataResponse;
import com.technogise.expensesharingapp.responseModels.DebtResponse;
import com.technogise.expensesharingapp.responseModels.ExpenseDebtResponse;
import com.technogise.expensesharingapp.responseModels.ExpenseResponse;
import com.technogise.expensesharingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseGeneratorImpl implements ResponseGenerator {

  @Autowired
  private UserService userService;

  @Override
  public ExpenseResponse expenseResponseGenerator(Expense expense) {
    ExpenseResponse expenseResponse = new ExpenseResponse();
    expenseResponse.setDescription(expense.getDescription());
    expenseResponse.setAmount(expense.getAmount());
    expenseResponse.setPayerName(userService.getUserById(expense.getPayerId()).get().getName());
    expenseResponse.setDateTime(expense.getCreatedAt());
    return expenseResponse;
  }

  @Override
  public DebtResponse debtResponseGenerator(Debt debt,User user) {
    DebtResponse debtResponse = new DebtResponse();
    debtResponse.setId(debt.getId());
    debtResponse.setAmount(debt.getAmount());
    if(user.getPhoneNumber() == userService.getUserById(debt.getCreditorId()).get().getPhoneNumber()) {
      debtResponse.setCreditor(null);
    }else {
      debtResponse.setCreditor(userService.getUserById(debt.getCreditorId()).get().getName());
    }
    if(user.getPhoneNumber() == userService.getUserById(debt.getDebtorId()).get().getPhoneNumber()) {
      debtResponse.setDebtor(null);
    }else {
      debtResponse.setDebtor(userService.getUserById(debt.getDebtorId()).get().getName());
    }
    return debtResponse;
  }

  @Override
  public AggregateDataResponse aggregateResponseGenerator
      (List<Expense> expenses, List<Debt> debts, User user, List<User> allUsers) {
    List<ExpenseResponse> expenseResponses = new ArrayList<ExpenseResponse>();
    for (int index = 0; index < expenses.size(); index++) {
      if (!expenses.get(index).getDescription().equals("Settlement")) {
        expenseResponses.add(expenseResponseGenerator(expenses.get(index)));
      }
    }
    List<DebtResponse> debtResponses = new ArrayList<DebtResponse>();
    for (int index = 0; index < debts.size(); index++) {
      if (debts.get(index).getAmount() != 0) {
        debtResponses.add(debtResponseGenerator(debts.get(index),user));
      }
    }
    allUsers.remove(user);
    AggregateDataResponse aggregateDataResponse = new AggregateDataResponse(expenseResponses, debtResponses, user, allUsers);
    return aggregateDataResponse;
  }
}