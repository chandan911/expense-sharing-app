package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.AddExpense;
import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.models.Expense;
import com.technogise.expensesharingapp.models.ExpenseDebtor;
import com.technogise.expensesharingapp.repositories.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DebtServiceImpl implements DebtService {

  @Autowired
  private DebtRepository debtRepository;

  @Autowired
  private ExpenseService expenseService;

  @Autowired
  private ExpenseDebtService expenseDebtService;

  @Override
  public List<Debt> getAllDebtsByUserId(Long userId) {
    return debtRepository.getAllDebtsByUserId(userId);
  }

  @Override
  public Boolean updateDebtRepository(Long payerId, Long debtorId, Double debtAmount) {

    Optional<Debt> checkDebt1 = debtRepository.getCreditorDebtorPair(payerId, debtorId);
    Optional<Debt> checkDebt2 = debtRepository.getCreditorDebtorPair(debtorId, payerId);
    if (!checkDebt1.isEmpty()) {
      Debt debt = checkDebt1.get();
      Double newAmount = debtAmount + debt.getAmount();
      debtRepository.updateDebt(debt.getId(), debt.getCreditorId(), debt.getDebtorId(), newAmount);
    } else if (!checkDebt2.isEmpty()) {

      Debt debt = checkDebt2.get();
      Double newAmount = debt.getAmount() - debtAmount;
      if (newAmount >= 0) {
        debtRepository.updateDebt(debt.getId(), debt.getCreditorId(), debt.getDebtorId(), newAmount);
      } else {
        debtRepository.updateDebt(debt.getId(), debt.getDebtorId(), debt.getCreditorId(), Math.abs(newAmount));
      }
    } else {
      debtRepository.save(new Debt(payerId, debtorId, debtAmount));
    }
    return true;
  }

  @Override
  public Boolean updateDebtProcess(AddExpense addExpense) {
    Expense expense = new Expense(addExpense.getDescription(), addExpense.getAmount(), addExpense.getPayerId());
    expense = expenseService.createExpense(expense);

    ArrayList<Long> debtorId = addExpense.getDebtorId();
    for (Integer id = 0; id < debtorId.size(); id++) {
      ExpenseDebtor expenseDebtor = new ExpenseDebtor(expense.getId(), debtorId.get(id));
      if (expenseDebtor.getDebtorId() != addExpense.getPayerId()) {
        expenseDebtService.createExpenseDebt(expenseDebtor);
        Double debtAmount = (addExpense.getAmount() / debtorId.size());
        updateDebtRepository(addExpense.getPayerId(), expenseDebtor.getDebtorId(), debtAmount);
      }
    }
    return true;
  }
}
