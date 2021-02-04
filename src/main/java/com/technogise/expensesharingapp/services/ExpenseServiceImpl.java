package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.Expense;
import com.technogise.expensesharingapp.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  @Autowired
  private ExpenseRepository expenseRepository;

  @Override
  public List<Expense> getAllExpensesByUserId(Long userId) {
    return expenseRepository.getAllExpensesById(userId);
  }
}
