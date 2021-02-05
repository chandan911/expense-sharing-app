package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.ExpenseDebtor;
import com.technogise.expensesharingapp.repositories.ExpenseDebtorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseDebtServiceImpl implements ExpenseDebtService {

    @Autowired
    private ExpenseDebtorRepository expenseDebtorRepository;

    @Override
    public ExpenseDebtor createExpenseDebt(ExpenseDebtor expenseDebtor) {
        return expenseDebtorRepository.save(expenseDebtor);
    }
}
