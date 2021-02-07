package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.repositories.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebtServiceImpl implements DebtService {

  @Autowired
  private DebtRepository debtRepository;

  @Override
  public List<Debt> getAllDebtsByUserId(Long userId) {
    return debtRepository.getAllDebtsByUserId(userId);
  }

  @Override
  public void updateDebtRepository(Long payerId, Long debtorId, Double debtAmount) {
    if (!(debtRepository.getCreditorDebtorPair(payerId, debtorId).isEmpty())) {

      Debt debt = debtRepository.getCreditorDebtorPair(payerId, debtorId).get();
      Double newAmount = debtAmount + debt.getAmount();
      debtRepository.updateDebt(debt.getId(), debt.getCreditorId(), debt.getDebtorId(), newAmount);

    } else if (!(debtRepository.getCreditorDebtorPair(debtorId, payerId).isEmpty())) {

      Debt debt = debtRepository.getCreditorDebtorPair(debtorId, payerId).get();
      Double newAmount = debt.getAmount() - debtAmount;
      if (newAmount >= 0) {
        debtRepository.updateDebt(debt.getId(), debt.getCreditorId(), debt.getDebtorId(), newAmount);
      } else {
        debtRepository.updateDebt(debt.getId(), debt.getDebtorId(), debt.getCreditorId(), Math.abs(newAmount));
      }
    } else {
      debtRepository.save(new Debt(payerId, debtorId, debtAmount));
    }
  }
}
