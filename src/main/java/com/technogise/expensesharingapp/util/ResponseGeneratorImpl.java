package com.technogise.expensesharingapp.util;

import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.models.Expense;
import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.responseModels.AggregateDataResponse;
import com.technogise.expensesharingapp.responseModels.DebtResponse;
import com.technogise.expensesharingapp.responseModels.ExpenseResponse;
import com.technogise.expensesharingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
  public DebtResponse debtResponseGenerator(Debt debt) {
    DebtResponse debtResponse = new DebtResponse();
    debtResponse.setId(debt.getId());
    debtResponse.setAmount(debt.getAmount());
    debtResponse.setCreditor(userService.getUserById(debt.getCreditorId()).get().getName());
    debtResponse.setDebtor(userService.getUserById(debt.getDebtorId()).get().getName());
    return debtResponse;
  }

  @Override
  public AggregateDataResponse aggregateResponseGenerator
      (List<Expense> expenses, List<Debt> debts, User user, List<User> allUsers) {
    List<ExpenseResponse> expenseResponses =
        expenses.stream().map(expense -> expenseResponseGenerator(expense)).collect( Collectors.toList() );
    List<DebtResponse> debtResponses = debts.stream().map(debt ->{
      DebtResponse debtResponse = debtResponseGenerator(debt);
      if(debtResponse.getCreditor().equals(user.getName())) debtResponse.setCreditor(null);
      if(debtResponse.getDebtor().equals(user.getName())) debtResponse.setDebtor(null);
      return debtResponse;
    }).collect(Collectors.toList());
    allUsers.remove(user);
    AggregateDataResponse aggregateDataResponse = new AggregateDataResponse(expenseResponses, debtResponses, user, allUsers);
    return aggregateDataResponse;
  }

}
