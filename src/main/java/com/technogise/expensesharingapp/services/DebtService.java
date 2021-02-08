package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.Debt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("debtService")
public interface DebtService {

  List<Debt> getAllDebtsByUserId(Long userId);

}
