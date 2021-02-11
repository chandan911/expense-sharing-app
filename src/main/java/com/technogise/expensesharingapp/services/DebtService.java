package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.NewExpenseRequest;
import com.technogise.expensesharingapp.models.Debt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("debtService")
public interface DebtService {

  List<Debt> getAllDebtsByUserId(Long userId);

  Boolean updateDebtRepository(Long payerId, Long debtorId, Double debtAmount);

  Boolean updateDebtProcess(NewExpenseRequest newExpenseRequest);

}
