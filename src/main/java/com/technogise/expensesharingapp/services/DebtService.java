package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.AddExpense;
import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.models.Expense;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("debtService")
public interface DebtService {

  List<Debt> getAllDebtsByUserId(Long userId);

  Boolean updateDebtRepository(Long payerId, Long debtorId, Double debtAmount);

  Boolean updateDebtProcess(AddExpense addExpense);

}
