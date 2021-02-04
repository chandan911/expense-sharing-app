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
}
