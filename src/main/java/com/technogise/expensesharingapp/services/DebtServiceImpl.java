package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.NewExpenseRequest;
import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.models.Expense;
import com.technogise.expensesharingapp.models.ExpenseDebtor;
import com.technogise.expensesharingapp.repositories.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    return debtRepository.findAllDebtsByUserId(userId);
  }

  @Override
  public Boolean updateDebtRepository(Long payerId, Long debtorId, Double debtAmount) {

    Optional<Debt> checkDebt1 = debtRepository.getCreditorDebtorPair(payerId, debtorId);
    Optional<Debt> checkDebt2 = debtRepository.getCreditorDebtorPair(debtorId, payerId);
    Date u = new Date();
    if (!checkDebt1.isEmpty()) {
      Debt debt = checkDebt1.get();
      Double newAmount = debtAmount + debt.getAmount();
      debtRepository.updateDebt(debt.getId(), debt.getCreditorId(), debt.getDebtorId(), newAmount,u);
    } else if (!checkDebt2.isEmpty()) {

      Debt debt = checkDebt2.get();
      Double newAmount = debt.getAmount() - debtAmount;
      if (newAmount >= 0) {
        debtRepository.updateDebt(debt.getId(), debt.getCreditorId(), debt.getDebtorId(), newAmount,u);
      } else {
        debtRepository.updateDebt(debt.getId(), debt.getDebtorId(), debt.getCreditorId(), Math.abs(newAmount),u);
      }
    } else {
      debtRepository.save(new Debt(payerId, debtorId, debtAmount));
    }
    return true;
  }

  @Override
  public Boolean updateDebtProcess(NewExpenseRequest newExpenseRequest) {
    Expense expense = new Expense(newExpenseRequest.getDescription(), newExpenseRequest.getAmount(), newExpenseRequest.getPayerId());
    expense = expenseService.createExpense(expense);

    ArrayList<Long> debtorId = newExpenseRequest.getDebtorIds();
    for (Integer id = 0; id < debtorId.size(); id++) {
      ExpenseDebtor expenseDebtor = new ExpenseDebtor(expense.getId(), debtorId.get(id));
      if (expenseDebtor.getDebtorId() != newExpenseRequest.getPayerId()) {
        expenseDebtService.createExpenseDebt(expenseDebtor);
        Double debtAmount = (newExpenseRequest.getAmount() / debtorId.size());
        updateDebtRepository(newExpenseRequest.getPayerId(), expenseDebtor.getDebtorId(), debtAmount);
      }
    }
    return true;
  }
}
