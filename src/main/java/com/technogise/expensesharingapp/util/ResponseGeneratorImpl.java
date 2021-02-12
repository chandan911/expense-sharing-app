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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseGeneratorImpl implements ResponseGenerator {

  @Autowired
  private UserService userService;

  static final String Settlement = "Settlement";

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

    List<ExpenseResponse> expenseResponses =
        expenses.stream().map(expense -> expenseResponseGenerator(expense)).filter(expenseResponse -> !expenseResponse.getDescription().equals(Settlement)).collect( Collectors.toList() );
    List<DebtResponse> debtResponses = debts.stream().filter(debt -> debt.getAmount()!=0).map(debt -> debtResponseGenerator(debt,user)).collect(Collectors.toList());
    allUsers.remove(user);
    AggregateDataResponse aggregateDataResponse = new AggregateDataResponse(expenseResponses, debtResponses, user, allUsers);
    return aggregateDataResponse;
  }
}